package Controllers;


/**
 * Created by Christopher on 8/24/16.
 */
abstract class ViewController {

    ViewController nextController = null;
    ViewController previousController = null;

    public ViewController() {
        AppController.getRunningInstance().registerViewController(this);
    }

    public void unregister() {
        AppController.getRunningInstance().unregisterViewController(this);
    }

    public abstract void step();

}
