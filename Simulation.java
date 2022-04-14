//Simulation.java
//Date: 20220415
//Author: Chloe Park and Marton Sharpe
//Purpose: Queueing Project Code for Simulation Class

import java.util.ArrayList;
import java.util.Scanner;

public class Simulation { //runs simulation of adjusting Class Ranking for Briarwood High School
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        ArrayList<Student> classRank = new ArrayList<Student>();
        ArrayList<Student> juniorList = new ArrayList<Student>();

        fillClassRanking(classRank); //fills class ranking w/ 1st Quarter data
        fillJuniorList(juniorList); //fills junior list w/ 2nd Quarter data

        System.out.println("First Name:");
        String userFirstName = in.nextLine();
        userFirstName = userFirstName.toUpperCase(); //for better readability

        System.out.println("Last Name:");
        String userLastName = in.nextLine();
        userLastName = userLastName.toUpperCase(); //for better readability

        String userFullName = userLastName + ", " + userFirstName; //combine user's last & first name to form full name

        System.out.println("Please select a sorting method:\n1. Alphabetically (Last Name)\n2. Numerically (G.P.A.)");
        int sortType = in.nextInt();

        System.out.println("");
        alterClassQueue(juniorList, classRank); //alters 1st quarter queue according to 2nd quarter data
        sortByMerge(classRank, 0, classRank.size()-1, sortType); //sorts new queue according to user's preference
        provideStudInfo(userFullName, userFirstName, userLastName, classRank); //informs if user is in class ranking
        System.out.println("");
        System.out.println("Percent of Juniors in Class Ranking: " + calculateRankPercent(juniorList, classRank) + "%");
        System.out.println("");
        printClassRank(classRank);
    }

    public static ArrayList<Student> alterClassQueue(ArrayList<Student> juniorList, ArrayList<Student> classRank){
        //adjusts 1st Quarter Class Ranking given new 2nd Quarter data

        Student nullStud = new Student("", 0); //creates a null student

        for (int i = juniorList.size()-1; i >= 0; i--) {
            for (int j = classRank.size()-1; j >= 0; j--){
                if (juniorList.get(i).fullName.compareToIgnoreCase(classRank.get(j).fullName) == 0){ //if a student
                    //is already in the 1st quarter class ranking
                    classRank.set(j, juniorList.get(i)); //replaces student's GPA w/ new 2nd Quarter data
                    if (classRank.get(j).grade < 3.70){ //if the student's 2nd Quarter GPA is less than 3.70
                        classRank.remove(j);
                    }
                    juniorList.set(i, nullStud); //sets the student's data in the larger junior list to null, so it
                    //isn't re-added during the final if-statement in the method
                }
            }
            if (juniorList.get(i).grade >= 3.70){ //for all students w/ GPA's of @ least 3.70 (not including those
                //already in class ranking
                classRank.add(juniorList.get(i)); //adds to end of the list
            }
        }
        return classRank;
    }

    public static void sortByMerge(ArrayList<Student> classRank, int firstIndex, int lastIndex, int sortType) {
        //sorts class ranking using merge sort & has option to be sorted alphabetically (last name) /numerically

        if (firstIndex == lastIndex){ //checks if list can't be split (list size = 1)
            return;
        }
        int middleIndex = (firstIndex + lastIndex)/2;

        //Recursion:
        sortByMerge(classRank, firstIndex, middleIndex, sortType); //dividing & sorting half of list
        sortByMerge(classRank, (middleIndex+1), lastIndex, sortType); //dividing & sorting half of list

        //Merge Method:
        mergeLists(classRank, firstIndex, middleIndex, lastIndex, sortType);
    }

    public static void mergeLists(ArrayList<Student> classRank, int indexF, int indexM, int indexL, int sortType) {
        //sorts the subLists either alphabetically (last name is compared 1st) or numerically
        //indexF = 1st index; indexM = middle index; indexL = last index - couldn't type out this due to spacing

        ArrayList<Student> tempList = new ArrayList<Student>(); //creates temporary array list of students
        int mainIndex = 0;
        int index1 = indexF; //starting index of 1st half of list
        int index2 = indexM + 1; //starting index of 2nd half of list

        if (sortType == 1){ //if sorting alphabetically
            while (index1 <= indexM && index2 <= indexL) { //sorting 2 half lists into 1 temp list
                if (classRank.get(index1).fullName.compareToIgnoreCase(classRank.get(index2).fullName) < 0) { //if
                    // name in 1st half list alphabetically precedes or is equal to 1st name in 2nd half list
                    tempList.add(mainIndex, classRank.get(index1));
                    index1++;
                } else { //if name in 2nd half list alphabetically precedes 1st name in 2nd half list
                    tempList.add(mainIndex, classRank.get(index2));
                    index2++;
                }
                mainIndex++;
            }
        }

        else{ //if sorting numerically
            while (index1 <= indexM && index2 <= indexL) { //sorting 2 half lists into 1 temp list
                if (classRank.get(index1).grade > classRank.get(index2).grade) { //if GPA in 1st half list
                    //is greater than GPA in 2nd half list
                    tempList.add(mainIndex, classRank.get(index1));
                    index1++;
                }
                else { //if GPA in 2nd half list is greater than GPA in 1st half list
                    tempList.add(mainIndex, classRank.get(index2));
                    index2++;
                }
                mainIndex++;
            }
        }

        while (index1 <= indexM) { //add remaining contents of 1st half list into temp list if not yet finished
            tempList.add(mainIndex, classRank.get(index1));
            index1++;
            mainIndex++;
        }
        while (index2 <= indexF) { //add remaining contents of 2nd half list into temp list if not yet finished
            tempList.add(mainIndex, classRank.get(index2));
            index2++;
            mainIndex++;
        }

        for (mainIndex = 0; mainIndex < tempList.size(); mainIndex++) { //transfer students in temp lists to class Rank
            classRank.set((indexF + mainIndex), tempList.get(mainIndex));
        }
    }

    public static void provideStudInfo(String name, String firstName, String lastName, ArrayList<Student> classRank){
        //confirms whether inputted user made class ranking & if so, provides his/her rank & GPA

        boolean inClassRanking = false;
        for (int i = 0; i<classRank.size()-1; i++){
            if (classRank.get(i).fullName.compareToIgnoreCase(name) == 0){ //checks if user is in class ranking
                inClassRanking = true;
                System.out.println(firstName + " " + lastName + " has made the Quarter 2 Class Ranking!");
                System.out.println("Rank #: " + (i+1) + "\nGPA: " + classRank.get(i).grade);
            }
        }
        if (!inClassRanking){
            System.out.println(firstName + " " + lastName + " has not made the Quarter 2 Class Ranking.");
        }
    }

    public static double calculateRankPercent(ArrayList<Student> juniorList, ArrayList<Student> classRank){
        //calculates % (percent) of all juniors who fit GPA requirement (made class ranking)

        return ((double) classRank.size()/juniorList.size()) * 100;
    }

    public static void printClassRank(ArrayList<Student> classRank){ //prints class rank in vertical list format
        for (int i = 0; i < classRank.size(); i++){
            System.out.print("#" + (i+1) + ": " + classRank.get(i).fullName + "  |  " + classRank.get(i).grade);
            System.out.println("");
        }
    }

    public static ArrayList<Student> fillClassRanking(ArrayList<Student> classRank) {
        //provides 1st Quarter Class Ranking data by adding it to an array list (in no order whatsoever)

        classRank.add(new Student("Korc, Noa", 4));
        classRank.add(new Student("Thompson, Jan", 4));
        classRank.add(new Student("Haen, Mirae", 3.97));
        classRank.add(new Student("Chow, Rania", 3.84));
        classRank.add(new Student("Fink, Matea", 3.8));
        classRank.add(new Student("Taylor, Chris", 3.78));
        classRank.add(new Student("James, Aaron", 3.73));
        classRank.add(new Student("Lee, Daniel", 3.71));
        return classRank;
    }

    public static ArrayList<Student> fillJuniorList(ArrayList<Student> juniorList) {
        //provides 2nd Quarter junior class data by adding it to an array list (in no order whatsoever)

        juniorList.add(new Student("Korc, Noa", 4));
        juniorList.add(new Student("Hong, Carla", 3.7));
        juniorList.add(new Student("Park, Sean", 3.5));
        juniorList.add(new Student("Adams, Amy", 2.1));
        juniorList.add(new Student("Jacobs, Henry", 1.64));
        juniorList.add(new Student("Chun, Emma", 3.02));
        juniorList.add(new Student("Fink, Matea", 3.85));
        juniorList.add(new Student("Byrd, Wyatt", 3.24));
        juniorList.add(new Student("Koonce, Jen", 2));
        juniorList.add(new Student("Cooper, Jill", 2.7));
        juniorList.add(new Student("Kerb, Lily", 3.11));
        juniorList.add(new Student("Chow, Rania", 3.75));
        juniorList.add(new Student("Francisco, Izzy", 3.43));
        juniorList.add(new Student("Pollard, Lexi", 3.6));
        juniorList.add(new Student("Lee, Daniel", 3.69));
        juniorList.add(new Student("Wayne, Bob", 3));
        juniorList.add(new Student("Hill, Arwin", 3.22));
        juniorList.add(new Student("Chen, Julia", 3.54));
        juniorList.add(new Student("Lawrence, Hailey", 2.99));
        juniorList.add(new Student("Fireman, Sophia", 2.6));
        juniorList.add(new Student("Haen, Mirae", 3.98));
        juniorList.add(new Student("Smith, Erwin", 2.8));
        juniorList.add(new Student("Black, Sophia", 3.5));
        juniorList.add(new Student("Johnson, Jerry", 3.2));
        juniorList.add(new Student("Garcia, Naomi", 3.68));
        juniorList.add(new Student("Franklin, Ben", 3.3));
        juniorList.add(new Student("Moore, Chase", 3.47));
        juniorList.add(new Student("Lee, Tommy", 4));
        juniorList.add(new Student("Clarkson, Jack", 2.85));
        juniorList.add(new Student("Allen, Larry", 3.11));
        juniorList.add(new Student("Campbell, Sarah", 2.8));
        juniorList.add(new Student("Green, Jake", 3.45));
        juniorList.add(new Student("Walker, Maya", 2.98));
        juniorList.add(new Student("James, Aaron", 3.75));
        juniorList.add(new Student("Davis, Candice", 3.6));
        juniorList.add(new Student("Wilson, Henry", 2.4));
        juniorList.add(new Student("Roberts, Lance", 2.6));
        juniorList.add(new Student("Palmer, Zoe", 3.44));
        juniorList.add(new Student("Nelson, Maria", 3.61));
        juniorList.add(new Student("Melton, Harry", 2.4));
        juniorList.add(new Student("Williams, Henry", 1.8));
        juniorList.add(new Student("Taylor, Chris", 3.64));
        juniorList.add(new Student("Sanchez, Hilary", 2.7));
        juniorList.add(new Student("Brown, Jacob", 3.2));
        juniorList.add(new Student("Thompson, Jan", 3.95));
        return juniorList;
    }

}

//Draft Code:
/*
    public static ArrayList<Student> sortGPA(ArrayList<Student> classRank) {
        Student currentStud = new Student("", 0);
        for (int i = 0; i < classRank.size()-1; i++){
            currentStud = classRank.get(i+1);
            int currentGradeIndex = i+1;

            for (int j = i; j>=0; j--){
                if (currentStud.grade < classRank.get(j).grade) {
                    classRank.set(currentGradeIndex, classRank.get(j));
                    classRank.set(j, currentStud);
                    currentGradeIndex--;
                }
            }
        }
        return classRank;
    }

     public static ArrayList<Student> sortNames(ArrayList<Student> classRank) {
        Student currentStud = new Student("", 0);
        for (int i = 0; i < classRank.size()-1; i++) {
            currentStud = classRank.get(i+1);
            int currentNameIndex = i+1;
            for(int j = i; j>=0; j--){
                if (currentStud.fullName.compareToIgnoreCase(classRank.get(j).fullName) < 0){
                    classRank.set(currentNameIndex, classRank.get(j));
                    classRank.set(j, currentStud);
                    currentNameIndex--;
                }
            }
        }
        return classRank;
    }
 */