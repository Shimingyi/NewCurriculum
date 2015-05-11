package cn.edu.sdu.online.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cn.edu.sdu.online.modal.Course;
import cn.edu.sdu.online.modal.Lesson;
import cn.edu.sdu.online.modal.MyHomework;
import cn.edu.sdu.online.modal.Person;
import cn.edu.sdu.online.modal.Question;
import cn.edu.sdu.online.modal.SignRelease;
import cn.edu.sdu.online.newcurriculum.R;
import cn.edu.sdu.online.utils.DateUtil;

public class Main extends Application {

    // 内存卡地址
    private static final String SDPATH = Environment
            .getExternalStorageDirectory() + "";
    private static final String COLORS = "colors";
    private static final String LOGIN = "login";
    public String[] curriculumArray;
    private Person person;
    private static Main app;
    private DateUtil date = new DateUtil();
    private final String CURRICULUM = "curriculum";
    private final static String CURRICULUMLIST = "curriculumlist";
    private List<List<Integer>> all = new ArrayList<List<Integer>>();
    private List<Lesson> lessonList;
    private final String TAG = "MAIN_APP";

    private Drawable[] colors;
    int[] drawable_colors = new int[]{R.drawable.color_1, R.drawable.color_2,
            R.drawable.color_3, R.drawable.color_4, R.drawable.color_5,
            R.drawable.color_6, R.drawable.color_7, R.drawable.color_8,
            R.drawable.color_9, R.drawable.color_10, R.drawable.color_11,
            R.drawable.color_12, R.drawable.color_13, R.drawable.color_14,
            R.drawable.color_15, R.drawable.color_16, R.drawable.color_17,
            R.drawable.color_18, R.drawable.color_19, R.drawable.color_20};
    private int[] intColors;

    @Override
    public void onCreate() {

        // TODO Auto-generated method stub
        super.onCreate();
        app = this;

    }

    public Drawable[] getColors() {
        colors = new Drawable[drawable_colors.length];
        for (int i = 0; i < drawable_colors.length; i++) {
            colors[i] = getResources().getDrawable(drawable_colors[i]);
        }
        return colors;
    }

    // 返回唯一的application对象
    public static Main getApp() {
        return app;
    }

    // 键值对仓库 包括文件标记、个人信息
    public SharedPreferences getDataStore() {
        return this.getSharedPreferences("NewCurriculum", MODE_PRIVATE);
    }

    public void setPerson(Person person) {
        this.person = person;
        // 保存账号密码！
        getDataStore().edit().putString("stuid", person.getMyStudentID())
                .putString("stuname", person.getMyName())
                .putString("academy", person.getMyAcademy())
                .putString("specialty", person.getMySpecialty())
                .putFloat("year_1", person.getFirstAveGrade())
                .putFloat("year_2", person.getSecondAveGrade())
                .putFloat("year_3", person.getThridAveGrade())
                .putFloat("year_4", person.getForthAveGrade()).commit();
    }

    public Person getPerson() {
        return person;
    }

    public void setCurriculumArray(List<Lesson> lessonList) {
        this.lessonList = lessonList;

        curriculumArray = new String[35];
        for (Lesson lesson : lessonList) {

            int position = lesson.getClassDayOfWeek() + 7
                    * (lesson.getClassDayOfTime() - 1) - 1;
            if (curriculumArray[position] != null)
                curriculumArray[position] += "\n" + lesson.getClassName()
                        + "\n" + lesson.getClassPlace();
            else
                curriculumArray[position] = lesson.getClassName() + "\n"
                        + lesson.getClassPlace();
        }

        number(lessonList);


        try {
            FileOutputStream out = openFileOutput(CURRICULUM, MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(curriculumArray);
            os.close();
            out.close();

            FileOutputStream outList = openFileOutput(CURRICULUMLIST, MODE_PRIVATE);
            ObjectOutputStream osList = new ObjectOutputStream(outList);
            osList.writeObject(lessonList);
            osList.close();
            outList.close();

            getDataStore().edit().putBoolean(CURRICULUM, true).commit();
            getDataStore().edit().putBoolean(CURRICULUMLIST, true).commit();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void setRankandComment(String filename, List<SignRelease> list) {

        try {
            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(list);
            os.close();
            out.close();
            getDataStore().edit().putBoolean(filename, true).commit();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void setAskAndAnswer(String filename, List<Question> list) {
        try {
            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(list);
            os.close();
            out.close();
            getDataStore().edit().putBoolean(filename, true).commit();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Question> getAskandAnswer(String filename) {
        if (getDataStore().getBoolean(filename, false)) {
            try {
                FileInputStream in = openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(in);
                List<Question> list = (List<Question>) is.readObject();
                is.close();
                in.close();
                return list;
            } catch (FileNotFoundException e) {
            } catch (StreamCorruptedException e) {
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        } else {
            return new ArrayList<Question>();
        }
        return new ArrayList<Question>();
    }

    public List<SignRelease> getRankandComment(String filename) {
        if (getDataStore().getBoolean(filename, false)) {
            try {
                FileInputStream in = openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(in);
                List<SignRelease> list = (List<SignRelease>) is.readObject();
                is.close();
                in.close();
                return list;
            } catch (FileNotFoundException e) {
            } catch (StreamCorruptedException e) {
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        } else {
            return new ArrayList<SignRelease>();
        }
        return new ArrayList<SignRelease>();
    }

    public List<Lesson> getCurriculumList() {
        if (lessonList != null) {
            return lessonList;
        }
        if (getDataStore().getBoolean(CURRICULUMLIST, false)) {
            try {

                FileInputStream in = openFileInput(CURRICULUMLIST);
                ObjectInputStream is = new ObjectInputStream(in);
                lessonList = (List<Lesson>) is.readObject();

                for(Lesson lesson:lessonList) {
                    Log.i(TAG, "lesson:"+lesson.toString());
                }
                is.close();
                in.close();
            } catch (FileNotFoundException e) {
            } catch (StreamCorruptedException e) {
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        } else {
            lessonList = new ArrayList<Lesson>();
        }
        return lessonList;

    }

    public String[] getCurriculumArray() {
        if (curriculumArray != null)
            return curriculumArray;


        if (getDataStore().getBoolean(CURRICULUM, false)) {
            Log.v("Main", "true");
            try {
                FileInputStream in = openFileInput(CURRICULUM);
                ObjectInputStream is = new ObjectInputStream(in);
                curriculumArray = (String[]) is.readObject();
                is.close();
                in.close();


            } catch (FileNotFoundException e) {
            } catch (StreamCorruptedException e) {
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        } else {
            curriculumArray = new String[35];
            return curriculumArray;
        }
        return curriculumArray;
    }

    // 得到颜色的数组

    public void number(List<Lesson> lessonList) {
        String namec[] = new String[35];
        boolean ifrepeat[] = new boolean[35];
        // all = new ArrayList<List<Integer>>();
        for (int ii = 0; ii < 35; ii++) {
            ifrepeat[ii] = false;
        }

        for (Lesson lesson : lessonList) {
            int position = lesson.getClassDayOfWeek() + 7
                    * (lesson.getClassDayOfTime() - 1) - 1;
            if (namec[position] != null) {
                namec[position] += lesson.getClassName();

            } else {
                namec[position] = lesson.getClassName();
            }
        }
        for (int i = 0; i < 35; i++) {
            List<Integer> save = new ArrayList<Integer>();
            if (namec[i] != null && ifrepeat[i] == false) {
                ifrepeat[i] = true;
                String temp = namec[i];
                save.add(i);
                for (int m = i + 1; m < 35; m++) {
                    if (namec[m] != null && ifrepeat[m] == false) {
                        if (namec[m].equals(temp)) {
                            ifrepeat[m] = true;
                            save.add(m);
                        }
                    }

                }
                all.add(save);
            }

        }
        Log.v("all", all.size() + "allsize");
    }

    public void clearAll() {
        getDataStore().edit().remove("COMPULSORY").remove("username")
                .remove("password").remove("stuid").remove("stuname")
                .remove("academy").remove("specialty").remove("year_1")
                .remove("year_2").remove("year_3").remove("year_4")
                .remove("FACEIMAGE").remove("FACESTORE").remove("edittalk")
                .remove("editsex").remove("editaim").remove("editname")
                .remove("ifsign").remove("signorder").remove("signrank")
                .remove("signcontinue").remove("lock").remove("Info_name")
                .remove("Info_sex").remove("Info_talktome").remove("Info_aim")
                .remove("iflock").remove("faceimagepath").remove("whichfrag")
                .remove("RANKANDCOMMENT").remove("HOMEWORK").remove("signday")
                .remove("signmonth").remove("signall").remove("isLogin")
                .remove("signif").remove("flagaskoldest")
                .remove("APPPACKAGENAME").remove("curriculum")
                .remove("ASKANDANSWER").commit();

    }

    // 头像写到sdcard中
    public void writetofile_faceimage(Bitmap bm) {
        ByteArrayOutputStream baos;
        FileOutputStream out;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);// png类型
            out = new FileOutputStream(new File(SDPATH + "/" + "faceimage.png"));
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 关于作业
    public void setHomework(String filename, ArrayList<MyHomework> homelist) {
        try {
            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(homelist);
            os.close();
            out.close();
            getDataStore().edit().putBoolean(filename, true).commit();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<MyHomework> getHomework(String filename) {
        if (getDataStore().getBoolean(filename, false)) {
            try {
                FileInputStream in = openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(in);
                ArrayList<MyHomework> list = (ArrayList<MyHomework>) is
                        .readObject();
                is.close();
                in.close();
                return list;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (StreamCorruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            return new ArrayList<MyHomework>();
        }
        return new ArrayList<MyHomework>();
    }

    public TreeMap<Integer, List<Course>> getMap() {
        return readFromFile("COMPULSORY");
    }

    public List<Course> getCompulsoryList(int semester) {
        return getMap().get(semester);

    }

    public void setCompulsoryList(TreeMap<Integer, List<Course>> compulsoryList) {
        if (compulsoryList == null) {
            compulsoryList = new TreeMap<Integer, List<Course>>();
        } else {
            writeToFile("COMPULSORY", compulsoryList);

        }
    }

    private void writeToFile(String filename,
                             TreeMap<Integer, List<Course>> treemap) {
        try {
            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(treemap);
            os.close();
            out.close();
            getDataStore().edit().putBoolean(filename, true).commit();
        } catch (FileNotFoundException e) {
            System.out.println(e + "yi chang shi");
        } catch (IOException e) {
            System.out.println("yi chang shi" + e);
        }

    }

    private TreeMap<Integer, List<Course>> readFromFile(String filename) {
        if (getDataStore().getBoolean(filename, false)) {
            try {
                FileInputStream in = openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(in);
                TreeMap<Integer, List<Course>> map = (TreeMap<Integer, List<Course>>) is
                        .readObject();
                is.close();
                in.close();
                return map;
            } catch (FileNotFoundException e) {
            } catch (StreamCorruptedException e) {
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        } else {
            return new TreeMap<Integer, List<Course>>();
        }
        return new TreeMap<Integer, List<Course>>();
    }

    // 超级学霸模式，获取时间
    public int getHour() {
        return date.getHour();
    }

    public int getSecond() {
        return date.getSecond();
    }

    public int getMinute() {
        return date.getMinute();
    }

    // 处理时间
    public int caloffset() {
        int offset;
        if (getHour() < 20) {
            offset = (19 - getHour()) * 60 * 60 * 1000 + (60 - getMinute())
                    * 60 * 1000 + (60 - getSecond()) * 1000;
        } else if (getHour() == 20 && getMinute() == 0 && getSecond() == 0) {
            offset = 0;
        } else {
            offset = (43 - getHour()) * 60 * 60 * 1000 + (60 - getMinute())
                    * 60 * 1000 + (60 - getSecond()) * 1000;
        }
        System.out.println("offset wei" + offset);
        return offset;
    }

    public int[] getIntColors() {

        if (getDataStore().getBoolean(COLORS, false)) {
            try {
                FileInputStream in = openFileInput(COLORS);
                ObjectInputStream is = new ObjectInputStream(in);
                intColors = (int[]) is.readObject();
                is.close();
                in.close();
            } catch (FileNotFoundException e) {
            } catch (StreamCorruptedException e) {
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        } else {
            if (getDataStore().getBoolean(LOGIN, false)) {
                generateRandomColors();
            }
        }
        return intColors;
    }

    public void generateRandomColors() {
        int a = 0;
        intColors = new int[35];
        Log.v("allsize", all.size() + "allsizeg");
        // Random ran = new Random();
        for (int i = 0; i < all.size(); i++) {
            for (int ii = 0; ii < all.get(i).size(); ii++) {
                intColors[all.get(i).get(ii)] = a;
            }
            a++;
        }

        // for (int i = 0; i < 35; i++) {
        // intColors[i] = ran.nextInt(7);
        // }
        try {
            FileOutputStream out = openFileOutput(COLORS, MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(intColors);
            os.close();
            out.close();
            getDataStore().edit().putBoolean(COLORS, true).commit();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    // 超级学霸模式
    public void setAppPackageName(String filename, ArrayList<String> list) {
        try {
            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(list);
            os.close();
            out.close();
            getDataStore().edit().putBoolean(filename, true).commit();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
