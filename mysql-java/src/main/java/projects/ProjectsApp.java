package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {

	private ProjectService projectService = new ProjectService();
	private Scanner scanner = new Scanner(System.in);
	private Project currentProject;
	// @formatter:off
		private List<String> operations = List.of(
			"1) Add a project"
		);
	// @formatter:on
		
		
	public static void main(String[] args) {
 
		new ProjectsApp().processUserSelections();
		
	}  // end main


	private void processUserSelections() {  // error catching of the user selection from the console
		boolean done = false;

		while (!done) {
			try {
				int selection = getUserSelection();
				
				switch (selection) {
				
				case -1 : 
					done = exitMenu();
					break;
				
				case 1 :
					createProject();
					break;
				
				
				default: System.out.println("\n" + selection + " is not a valid selection. Try again.");
				break;
				}
			}
			catch(Exception e) {
				System.out.println("\nError: " + e + " Try again.");
			}
		}
	} // end method processUserSelections


	private void createProject() { // create project from user input
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();  //call default constructor
		// set project parameters 
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		// add project to project Service
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}


	
	private boolean exitMenu() {
	System.out.println("\nExiting the menu. Goodbye.");
		return true;
	}


	private int getUserSelection() {
		printOperations();
		
		Integer input =  getIntInput("Enter a menu selection");
		
		return Objects.isNull(input) ? -1 : input;
	} // end method getUserSelection


	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if (Objects.isNull(input)) {
		return null;
		}
		
		try {
			return Integer.valueOf(input); //check if input is int
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	} // end method getIntInput

	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if (Objects.isNull(input)) {
		return null;
		}
		
		try {
			return new BigDecimal(input).setScale(2); 
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	} // end method getDecimalInput

	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
				
		return input.isBlank() ? null : input.trim();  // if input is blank return null, else trim the blanks from the end of the line
		
	}


	private void printOperations() { // prints out the available user options as defined in operations List object
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");
		operations.forEach(line -> System.out.println("\t" + line));
		/*if (Objects.isNull(currentProject)) {
			System.out.println("\nYou do not have an active project.");
		} else {
			System.out.println("\n You are viewing: " + currentProject);
		} */
	} // end method printOperations

}
