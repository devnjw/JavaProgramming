package edu.handong.analysis;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Code;
import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysise.utils.NotEnoughArgumentException;
import edu.handong.analysise.utils.Utils;
import org.apache.commons.cli.*;

public class HGUCoursePatternAnalyzer {
	String input;
	String output;
	String analysis;
	String coursecode;
	String startyear;
	String endyear;
	boolean help;

	private HashMap<String,Student> students;

	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		/*try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}*/




		Options options = createOptions();
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			//String data

            String dataPath = input; // csv file to be analyzed
            String resultPath = output; // the file path where the results are saved.
            ArrayList<String> lines = Utils.getLines(dataPath, true);

            students = loadStudentCourseRecords(lines);

            // To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
            Map<String, Student> sortedStudents = new TreeMap<String,Student>(students);


			if(Integer.parseInt(analysis )== 1) {
				System.out.println("Analyzer 1 is working now...");
				// Generate result lines to be saved.
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
				// Write a file (named like the value of resultPath) with linesTobeSaved.
				Utils.writeAFile(linesToBeSaved, resultPath);
			}else if(Integer.parseInt(analysis) == 2){
                System.out.println("Analyzer 2 is working now...");
                // Generate result lines to be saved.
                ArrayList<String> linesToBeSaved = courseTakenInfo(sortedStudents, lines);
                // Write a file (named like the value of resultPath) with linesTobeSaved.
                Utils.writeAFile(linesToBeSaved, resultPath);
			}else
				System.out.println("Wrong number. \"-a\" must be between 1 and 10.");
		}
	}


	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		
		// TODO: Implement this method
		HashMap<String,Student> students = new HashMap<String,Student>();

		Student student;
		for(int i = 0; i < Integer.parseInt(lines.get(lines.size()-1).split(", ")[0]); i++) {
			student = new Student(String.format("%04d", i+1));
			students.put(String.format("%04d", i+1), student);
		}

		//Course course;
		for(String line:lines) {
            int year = Integer.parseInt(line.split(", ")[7]);
            //System.out.println(year);
		    if(year >= Integer.parseInt(startyear) && year <= Integer.parseInt(endyear)) {
                //System.out.println(line.split(", ")[0] + " : " + year);
                Course course = new Course(line);
                students.get(course.getStudentId()).addCourse(course);
            }
		}

		return students; // do not forget to return a proper variable.

	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
    private ArrayList<String> courseTakenInfo(Map<String, Student> sortedStudents, ArrayList<String> lines){
        ArrayList<String> arrayList = new ArrayList<>();
        HashMap<String, Code> codes = new HashMap<>();
        Code code;
        for(int i = Integer.parseInt(startyear); i <= Integer.parseInt(endyear); i++){
            for(int j = 1; j <= 4; j++){
                code = new Code(i, j, coursecode);
                codes.put((i + "-" + j), code);
            }
        }

        /*for(int i = Integer.parseInt(startyear); i <= Integer.parseInt(endyear); i++){
            for(int j = 1; j <= 4; j++){
                String key = i + "-" + j;
                int count = 0;
                int studentsTaken = 0;
                for(int l = 0; l < sortedStudents.size(); l++) { // 학생
                    ArrayList<Course> coursesTaken = sortedStudents.get(String.format("%04d", l + 1)).getCoursesTaken();
                    int on = 0;
                    for(int l2 = 0; l2 < coursesTaken.size(); l2++) { // 수업
                        if (coursesTaken.get(l2).getYearTaken() == i && coursesTaken.get(l2).getSemesterCourseTaken() == j) {
                            if (on != 1) {
                                count++;
                                on = 1;
                            }
                            if (coursesTaken.get(l2).getCourseCode().equals(coursecode))
                                studentsTaken++;
                        }
                    }
                }
                System.out.println("count : " + count + " studentsTaken : " + studentsTaken);
                totalStudentsInSemester.put(key, count + 1000*studentsTaken);
            }
        }*/

        // get course name


        /*arrayList.add("Year, Semester, CouseCode, CourseName, TotalStudents, StudentsTaken, Rate");
        for(int i = Integer.parseInt(startyear); i <= Integer.parseInt(endyear); i++) {
            for (int j = 1; j <= 4; j++) {
                String courseName;
                for(String line:lines) {
                    System.out.println(coursecode + line.split(", ")[4]);
                    if (line.split(", ")[4] == coursecode) {
                        courseName = line.split(", ")[5];
                        arrayList.add(i + ", " + j + ", " + coursecode + courseName + totalStudentsInSemester.get(i + "-" + j) % 1000 + totalStudentsInSemester.get(i + "-" + j) / 1000 + (totalStudentsInSemester.get(i + "-" + j) % 1000) / (totalStudentsInSemester.get(i + "-" + j) / 1000));
                        System.out.println(i + ", " + j + ", " + coursecode + courseName + totalStudentsInSemester.get(i + "-" + j) % 1000 + totalStudentsInSemester.get(i + "-" + j) / 1000 + (totalStudentsInSemester.get(i + "-" + j) % 1000) / (totalStudentsInSemester.get(i + "-" + j) / 1000));
                        break;
                    }
                }
            }
        }*/



       /*for(int i = 0; i < sortedStudents.size(); i++){
            ArrayList<Course> coursesTaken = sortedStudents.get(String.format("%04d", i+1)).getCoursesTaken();
            for(int j = 0; j < coursesTaken.size(); j++){
                if(coursesTaken.get(j).getCourseCode().equals(coursecode)){
                    arrayList.add(coursesTaken.get(j).getYearTaken() + ", " + coursesTaken.get(j).getSemesterCourseTaken() + ", " + coursecode + ", " + coursesTaken.get(j).getCourseName());
                }
            }
        }*/
        return arrayList;
    }

	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		// TODO: Implement this method
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");

		for(int i = 0; i < sortedStudents.size(); i++){
			sortedStudents.get(String.format("%04d", i+1)).setSemestersByYearAndSemester();
			for(int j = 0; j < sortedStudents.get(String.format("%04d", i+1)).getSemestersByYearAndSemester().size(); j++)
				arrayList.add(sortedStudents.get(String.format("%04d", i+1)).getStudentId() + ", " + sortedStudents.get(String.format("%04d", i+1)).getSemestersByYearAndSemester().size() + ", " + Integer.toString(j+1) + ", " + Integer.toString(sortedStudents.get(String.format("%04d", i+1)).getNumCourseInNthSemester(j+1)));
		}

		return arrayList; // do not forget to return a proper variable.
	}


	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			analysis = cmd.getOptionValue("a");
			coursecode = cmd.getOptionValue("c");
			startyear = cmd.getOptionValue("s");
			endyear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			//System.out.println("Error");
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Path name to read")
				.required()
				.build());
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Path name to write")
				.required()
				.build());
		options.addOption(Option.builder("a").longOpt("analyzer")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Count courses per what")
				.required()
				.build());
		options.addOption(Option.builder("c").longOpt("course")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("Course code")
				.build());
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("startyear")
				.required()
				.build());
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("endyear")
				.required()
				.build());

/*		// add options by using OptionBuilder
		options.addOption(Option.builder("v").longOpt("verbose")
				.desc("Display detailed messages!")
				//.hasArg()     // this option is intended not to have an option value but just an option
				.argName("verbose option")
				//.required() // this is an optional option. So disabled required().
				.build());*/

		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());

		return options;
	}

	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}
}