import UIKit

class MainTabBarController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()

        let issueItem = UITabBarItem(title: "Issue", image: nil, tag: 0)
        let labelItem = UITabBarItem(title: "Label", image: nil, tag: 1)
        let milestoneItem = UITabBarItem(title: "Milestone", image: nil, tag: 2)

        let presenter = TableViewControllerPresenter(title: "Issue", tabBarItem: labelItem)
        presenter.add(in: self)
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

struct TableViewControllerPresenter {
    var storyboard = UIStoryboard(name: "Main", bundle: nil)
    let title: String
    let tabBarItem: UITabBarItem

    func add(in viewController: UITabBarController) {
        let tableViewController = storyboard.instantiateViewController(withIdentifier: String(describing: TableViewController.self)) as! TableViewController
        tableViewController.tabBarItem = tabBarItem
        tableViewController.titleText = title
        viewController.viewControllers?.append(tableViewController)
    }
}
