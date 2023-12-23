package game;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;

public class GameFrame extends JFrame implements KeyListener, ActionListener {
    // JFrame表示界面，窗体
    // 他的子类也是界面和窗体
    // 规定: GameJFrame就是游戏的主页面
    // 以后和游戏有关的所有逻辑都写在这个类中

    int[][] data= new int[4][4];

    // 记录空白方块在数组中的位置
    int x=0;
    int y=0;

    // 定义一个变量 记录当前展示图片的路径
    String path = "image\\Cat\\cat2\\";

    // 定义一个二维数组，存储正确的数据
    int[][] win={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    // 定义变量统计步数
    int step =0;

    // 创建下面的条目对象
    JMenuItem replayItem = new JMenuItem("restart game");
    JMenuItem closeItem = new JMenuItem("close game");
    JMenuItem accountItem = new JMenuItem("My selfie");

    // 创建图片选项
    JMenuItem TFItem = new JMenuItem("TFboys");
    JMenuItem CatItem = new JMenuItem("Cat");
    JMenuItem ikunItem = new JMenuItem("ikun");

    public GameFrame(){


        // 初始化界面
        initJFrame();

        // 初始化菜单
        initJMenuBar();

        // 初始化数据(打乱)
        initData();

        // 初始化图片
        initImage();

        // 显示用户指南
        initialInstruction();


        // 显示写在最后
        this.setVisible(true);
    }

    private void initialInstruction(){
        JDialog jD = new JDialog();
        // 创建一个管理图片的容器对象
        JLabel jLabel = new JLabel(new ImageIcon("sporter\\instruction.png"));
        jLabel.setBounds(0,0,584,312);
        // 把图片添加到弹框之中
        jD.getContentPane().add(jLabel);
        // 给弹窗设置大小
        jD.setSize(600,350);
        // 让弹框置顶
        jD.setAlwaysOnTop(true);
        // 让弹框显示出来
        jD.setVisible(true);

    }



    // 打乱数据
    private void initData() {
        int[] tempArr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

        // 遍历数组，得到每一个元素，拿着每一个元素跟随机索引上的数据进行交换
        Random r = new Random();
        for (int i = 0;i<tempArr.length;i++){
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;

        }

        for (int i = 0;i<tempArr.length;i++){
            if (tempArr[i]==0){
                x=i/4;
                y=i%4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }

    }

    // 初始化图片
    // 按照二维数组中管理的数据添加图片
    private void initImage() {
        // 清空原本已经出现的所有图片
        this.getContentPane().removeAll();

        if (victory()){
            // 显示胜利图标
            JLabel winJLabel = new JLabel(new ImageIcon("sporter\\win.jpg"));
            winJLabel.setBounds(190,283,288,175);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount = new JLabel("steps counter: "+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);
        // 绝对路径: 一定从盘符开始  e.g. C:\\
        // 相对路径: 不是从盘符开始: aaa\\ bbb, 指的是在当前项目下，去找aaa文件夹，再找bbb

        // 外循环---把内循环重复执行四次
        for (int i=0;i<4;i++){
            // 内循环---表示在一行添加4张图片
            for (int j=0;j<4;j++){
                // 获取当前要加载图片的序号
                int num = data[i][j];
                // 创建一个JLabel的对象(管理容器)

                JLabel jLabel = new JLabel(new ImageIcon(path+num+".jpg"));

                // 给图片添加边框
                jLabel.setBorder(new BevelBorder(1));
                // 指定图片位置
                jLabel.setBounds(105*j+83,105*i+134,105,105);

                // 把管理容器添加到界面
                //this.add(jLabel);
                this.getContentPane().add(jLabel);

                // 刷新一下界面
                this.getContentPane().repaint();
            }
        }
        // 添加背景图片
        ImageIcon bg = new ImageIcon("sporter\\background.jpg");
        JLabel background = new JLabel(bg);
        background.setBounds(40,40,508,560);
        // 把背景图片添加到界面中
        this.getContentPane().add(background);

    }


    private void initJMenuBar() {
        // 创建整个菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        // 创建菜单式的两个选项的对象(功能，关于我们)
        JMenu functionJMenu = new JMenu("Function");
        JMenu aboutJMenu = new JMenu("About developer");

        // 创建更改图片的选项功能
        JMenu changeMenu = new JMenu("change Picture");


        // 将每一个选项下面的条目添加到选项中
        functionJMenu.add(replayItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        // 给条目绑定事件
        replayItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        TFItem.addActionListener(this);
        CatItem.addActionListener(this);
        ikunItem.addActionListener(this);

        // 将菜单里面的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        // 把图片选项添加到更改图片菜单中
        changeMenu.add(TFItem);
        changeMenu.add(CatItem);
        changeMenu.add(ikunItem);

        functionJMenu.add(changeMenu);


        // 给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        this.setSize(603,680);
        // 设置界面的标题
        this.setTitle("Puzzle-offline v1.0");
        // 设置界面置顶
        this.setAlwaysOnTop(true);
        // 设置界面居中
        this.setLocationRelativeTo(null);
        // 设置关闭模式
        this.setDefaultCloseOperation(3);

        // 取消默认的居中位置，只有取消了才会按照XY轴的形式添加图片
        this.setLayout(null);
        // 给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 按下不松调用这个方法
    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        if (code==65){
            // 把界面里所有的图片删除
            this.getContentPane().removeAll();
            // 加载第一张完整图片
            JLabel all= new JLabel(new ImageIcon(path+"all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            // 加载背景图片
            ImageIcon bg = new ImageIcon("sporter\\background.jpg");
            JLabel background = new JLabel(bg);
            background.setBounds(40,40,508,560);
            // 把背景图片添加到界面中
            this.getContentPane().add(background);
            // 刷新界面
            this.getContentPane().repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 判断游戏是否胜利，如果胜利，此方法结束，不能再执行下面的移动代码
        if (victory()){
            // 1返回值 2结束方法
            return;
        }
        // 对上下左右进行判断
        // 左: 37 上:38 右:39 下:40
        int code =e.getKeyCode();
        if (code==37){
            System.out.println("向左移动");
            if (y==3){
                return;
            }
            // 逻辑: 把空白块左方的数字往右移动
            data[x][y] = data[x][y+1];
            data[x][y+1]=0;
            y++;
            // 每移动一次，计数器自增一次
            step++;
            // 调用方法按照最新的数字加载图片
            initImage();
        } else if (code==38) {
            // 每移动一次，计数器自增一次
            step++;
            System.out.println("向上移动");
            if (x==3){
                // 说明空白方块已经在最下方了，下面没有图片可以移动啦
                return;
            }
            // 逻辑: 把空白块下方的数字往上移动
            // x，y表示空白方块
            // 把空白方块下方的数字赋值给空白方块
            data[x][y] = data[x+1][y];
            data[x+1][y]=0;
            x++;
            // 调用方法按照最新的数字加载图片
            initImage();

        } else if (code==39) {
            // 每移动一次，计数器自增一次
            step++;
            System.out.println("向右移动");
            if (y==0){
                return;
            }
            // 逻辑: 把空白块左方的数字往右移动
            data[x][y] = data[x][y-1];
            data[x][y-1]=0;
            y--;
            // 调用方法按照最新的数字加载图片
            initImage();


        } else if (code==40) {
            // 每移动一次，计数器自增一次
            step++;
            System.out.println("向下移动");
            if (x==0){
                return;
            }
            // 逻辑: 把空白块上方的数字往右移动
            data[x][y] = data[x-1][y];
            data[x-1][y]=0;
            x--;
            // 调用方法按照最新的数字加载图片
            initImage();

        } else if (code==65) {
            initImage();
        } else if (code==87) {
            data=new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();
        }


    }

    // 判断data数组的数据是否和win相同
    public boolean victory(){
        for (int i=0; i<data.length;i++){
            for (int j=0; j<data[i].length;j++){
                if (data[i][j] != win[i][j]){
                    // 只要一个数据不一样就返回false
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取当前被点击的条目对象
        Object obj = e.getSource();

        // 判断
        if (obj==replayItem){
            // 计步器归零
            step=0;

            // 再次打乱二维数组中的数据
            initData();
            // 重新加载游戏
            initImage();

        } else if (obj==closeItem) {
            // 直接关闭虚拟机
            System.exit(0);
        }else if(obj==accountItem){
            // 创建一个弹窗对象
            JDialog jDialog = new JDialog();
            // 创建一个管理图片的容器对象
            JLabel jLabel = new JLabel(new ImageIcon("sporter\\information1.jpg"));
            jLabel.setBounds(0,0,420,420);
            // 把图片添加到弹框之中
            jDialog.getContentPane().add(jLabel);
            // 给弹窗设置大小
            jDialog.setSize(400,400);
            // 让弹框置顶
            jDialog.setAlwaysOnTop(true);
            // 让弹框居中
            jDialog.setLocationRelativeTo(null);
            // 弹框不关闭就无法操作下面的界面
            jDialog.setModal(true);
            // 让弹框显示出来
            jDialog.setVisible(true);
        } else if (obj==TFItem) {
            Random r = new Random();
            int num=r.nextInt(4)+1;
            path="image\\TFboys\\tf"+num+"\\";
            initImage();
        } else if (obj==CatItem) {
            Random r = new Random();
            int num=r.nextInt(3)+1;
            path="image\\Cat\\cat"+num+"\\";
            initImage();
        }else if(obj==ikunItem) {
            Random r = new Random();
            int num = r.nextInt(4) + 1;
            path = "image\\ikun\\ikun" + num + "\\";
            initImage();
        }

    }
}