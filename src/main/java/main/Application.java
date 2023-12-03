package main;

import config.Config;
import view.MainWindow;

public class Application
{
    public Application(Config config)
    {
        Model model_ = new Model(config);
        MainWindow view_ = new MainWindow(model_);
        new Controller(model_, view_);
    }
}
