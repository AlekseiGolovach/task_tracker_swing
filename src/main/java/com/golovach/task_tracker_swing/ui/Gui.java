package com.golovach.task_tracker_swing.ui;

import com.golovach.task_tracker_swing.dto.EmployeeDto;
import com.golovach.task_tracker_swing.dto.TaskDto;
import com.golovach.task_tracker_swing.dto.facade.EmployeeFacade;
import com.golovach.task_tracker_swing.service.EmployeeService;
import com.golovach.task_tracker_swing.store.entities.EmployeeEntity;
import com.golovach.task_tracker_swing.ui.table_models.PlanTableModel;
import com.golovach.task_tracker_swing.ui.table_models.TaskTableModel;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@org.springframework.stereotype.Component
public class Gui {
    private final JFrame mainFrame;

    private final EmployeeFacade employeeFacade;
    private final EmployeeService employeeService;
    private final JLabel fieldNameJComboBoxEmployees;
    private final JComboBox<String> jComboBoxEmployees;
    private final JTabbedPane jTabbedPaneForPlansTasks;
    private final JTable jTableTasks;
    private final JLabel fieldNameJComboBoxMonth;
    private final JComboBox<String> jComboBoxMonth;
    private final JTable jTablePlane;
    private final JScrollPane jScrollPaneForJTableTasks;
    private final JScrollPane jScrollPaneForJTablePlane;
    private final TaskTableModel taskTableModel;
    private final PlanTableModel planTableModel;
    private final List<EmployeeDto> employeeDtoList;
    private List<TaskDto> currentMonthTaskDtoList;
    private EmployeeDto currentEmployeeDto;
    private int currentMonthIndex;

    public Gui(EmployeeFacade employeeFacade, EmployeeService employeeService) {
        this.employeeFacade = employeeFacade;
        this.employeeService = employeeService;
        List<EmployeeEntity> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            log.error("Employees database is empty.");
            throw new RuntimeException("Employees database is empty.");
        }
        this.employeeDtoList = employees
                .stream()
                .map(employee -> employeeFacade.employeeToEmployeeDto(employee))
                .collect(Collectors.toList());

        mainFrame = new JFrame();

        fieldNameJComboBoxEmployees = new JLabel();
        jComboBoxEmployees = new JComboBox<>(
                employeeDtoList.stream()
                        .map((EmployeeDto::getFullName))
                        .toArray(String[]::new)
        );

        jTabbedPaneForPlansTasks = new JTabbedPane();

        taskTableModel = new TaskTableModel();
        jTableTasks = new JTable(taskTableModel);
        jScrollPaneForJTableTasks = new JScrollPane(jTableTasks);


        fieldNameJComboBoxMonth = new JLabel();
        jComboBoxMonth = new JComboBox<>(new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"});
        planTableModel = new PlanTableModel();
        jTablePlane = new JTable(planTableModel);
        jScrollPaneForJTablePlane = new JScrollPane(jTablePlane);

        configPosition();
        configParams();
        configListeners();

        createGui();
    }

    private void createGui() {
        setEmployeeDto(employeeDtoList.get(0));
        setMonth("Январь");

        mainFrame.setVisible(true);
    }

    private void setEmployeeDto(EmployeeDto employeeDto) {
        currentEmployeeDto = employeeDto;

        taskTableModel.removeAllRow();
        planTableModel.removeAllRow();
        for (TaskDto taskDto : employeeDto.getTasks()) {
            taskTableModel.addData(getTaskRow(taskDto));
        }

        jComboBoxMonth.setSelectedIndex(0);

        taskTableModel.fireTableDataChanged();
    }

    private void setMonth(String month) {
        currentMonthIndex = getMonthIndex(month);
        planTableModel.removeAllRow();
        currentMonthTaskDtoList = new ArrayList<>();
        for (TaskDto taskDto :
                currentEmployeeDto.getTasks()) {
            LocalDate startDate = taskDto.getStartDate();
            LocalDate endDate = taskDto.getEndDate();
            if (currentMonthIndex >= startDate.getMonthValue() &&
                    currentMonthIndex <= endDate.getMonthValue()) {
                planTableModel.addData(taskDtoRowGenerator(taskDto));

                currentMonthTaskDtoList.add(taskDto);
            }

        }

        LocalDate localDate = LocalDate.of(2024, currentMonthIndex, 1);
        String countOfDays = TemporalAdjusters.lastDayOfMonth().adjustInto(localDate).toString().split("-")[2];
        planTableModel.setColumnCount(Integer.parseInt(countOfDays));
        planTableModel.fireTableStructureChanged();
        planTableModel.fireTableDataChanged();
        taskTableModel.fireTableDataChanged();
        jTablePlane.getColumnModel().getColumn(0).setMinWidth(120);
        colorTablePlane();

    }

    private List<String> taskDtoRowGenerator(TaskDto taskDto) {
        List<String> taskRow = new ArrayList<>();
        taskRow.add(taskDto.getName());
        for (int i = 0; i < 31; i++) {
            taskRow.add("");
        }

        int startMonth = taskDto.getStartDate().getMonthValue();
        int endMonth = taskDto.getEndDate().getMonthValue();

        if (currentMonthIndex > startMonth && currentMonthIndex < endMonth) {
            for (int i = 1; i < taskRow.size(); i++) {
                taskRow.set(i, " ");
            }
        }
        if (currentMonthIndex == endMonth && currentMonthIndex == startMonth) {
            for (int i = taskDto.getStartDate().getDayOfMonth(); i < taskDto.getEndDate().getDayOfMonth(); i++) {
                taskRow.set(i, " ");
            }
        }else {
            if (currentMonthIndex == startMonth) {
                for (int i = taskDto.getStartDate().getDayOfMonth(); i < taskRow.size(); i++) {
                    taskRow.set(i, " ");
                }
            }

            if (currentMonthIndex == endMonth) {
                for (int i = 1; i < taskDto.getEndDate().getDayOfMonth(); i++) {
                    taskRow.set(i, " ");
                }
            }
        }
        return taskRow;
    }

    private void colorTablePlane() {
                for (int column = 0; column < jTablePlane.getColumnCount(); column++) {
                    jTablePlane.getColumnModel().getColumn(column).setCellRenderer(new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                            if (" ".equals((String) value)) {
                                component.setBackground(Color.LIGHT_GRAY);
                            }
                            else {
                                component.setBackground(Color.WHITE);
                            }
                            return component;
                        }
                    });
                }
    }

    private void configPosition() {
        JPanel comboBoxEmployees = new JPanel();
        comboBoxEmployees.add(fieldNameJComboBoxEmployees);
        comboBoxEmployees.add(jComboBoxEmployees);
        comboBoxEmployees.setLayout(new FlowLayout());

        JPanel comboBoxMonth = new JPanel();
        comboBoxMonth.add(fieldNameJComboBoxMonth);
        comboBoxMonth.add(jComboBoxMonth);
        comboBoxMonth.setLayout(new FlowLayout());

        JPanel jPanelForComboBoxMonthAndJTablePlan = new JPanel();
        jPanelForComboBoxMonthAndJTablePlan.setLayout(new GridBagLayout());
        jPanelForComboBoxMonthAndJTablePlan.add(comboBoxMonth,
                new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        jPanelForComboBoxMonthAndJTablePlan.add(jScrollPaneForJTablePlane,
                new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));

        jTabbedPaneForPlansTasks.add(jScrollPaneForJTableTasks);
        jTabbedPaneForPlansTasks.add(jPanelForComboBoxMonthAndJTablePlan);

        mainFrame.setLayout(new GridBagLayout());
        mainFrame.add(comboBoxEmployees,
                new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        mainFrame.add(jTabbedPaneForPlansTasks,
                new GridBagConstraints(0, 1, 2, GridBagConstraints.REMAINDER, 1, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));

    }

    private void configListeners() {
        jComboBoxEmployees.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setEmployeeDto(employeeDtoList
                        .stream()
                        .filter((employee) -> {
                            return employee.getFullName().equals((String) e.getItem());
                        })
                        .findFirst()
                        .orElseThrow());
            }
        });

        jComboBoxMonth.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setMonth((String) e.getItem());

            }
        });
    }

    private void configParams() {
        mainFrame.setSize(800, 400);
        mainFrame.setTitle("Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);


        jTableTasks.getColumnModel().getColumn(0).setMaxWidth(80);

        jTablePlane.getColumnModel().getColumn(0).setMinWidth(100);
        jScrollPaneForJTablePlane.setMinimumSize(new Dimension(300, 500));

        jTabbedPaneForPlansTasks.setTitleAt(0, "Задачи");
        jTabbedPaneForPlansTasks.setTitleAt(1, "План");

        fieldNameJComboBoxEmployees.setText("Сотрудник: ");
        fieldNameJComboBoxMonth.setText("Месяц: ");
    }

    private static String localDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    private List<String> getTaskRow(TaskDto task) {
        return List.of(task.getIsDone(), task.getName(), localDateToString(task.getStartDate()), localDateToString(task.getEndDate()), localDateToString(task.getExecutionDate()));
    }

    private int getMonthIndex(String month) {
        int monthIndex = 0;
        if (month.equals("Январь")) monthIndex = 1;
        if (month.equals("Февраль")) monthIndex = 2;
        if (month.equals("Март")) monthIndex = 3;
        if (month.equals("Апрель")) monthIndex = 4;
        if (month.equals("Май")) monthIndex = 5;
        if (month.equals("Июнь")) monthIndex = 6;
        if (month.equals("Июль")) monthIndex = 7;
        if (month.equals("Август")) monthIndex = 8;
        if (month.equals("Сентябрь")) monthIndex = 9;
        if (month.equals("Октябрь")) monthIndex = 10;
        if (month.equals("Ноябрь")) monthIndex = 11;
        if (month.equals("Декабрь")) monthIndex = 12;

        return monthIndex;
    }

}
