import UIKit

class MainTabBarController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()

        let issueItem = UITabBarItem(title: "Issue", image: nil, tag: 0)
        let labelItem = UITabBarItem(title: "Label", image: nil, tag: 1)
        let milestoneItem = UITabBarItem(title: "Milestone", image: nil, tag: 2)


        let issueVC = TabBarControllerAdder<LabelListViewController>(title: "Issue", tabBarItem: labelItem)
        let milestoneVC = TabBarControllerAdder<MilestoneListViewController>(title: "Milestone", tabBarItem: milestoneItem)
        issueVC.add(in: self)
        milestoneVC.add(in: self)
    }

}

struct TabBarControllerAdder<ViewController> where ViewController: UIViewController {
    var storyboard = UIStoryboard(name: Identifier.Storyboard.main, bundle: nil)
    let title: String
    let tabBarItem: UITabBarItem

    func add(in tabBarController: UITabBarController) {
        let tableViewController = storyboard.instantiateViewController(identifier: Identifier.ViewController.list) { coder in
            ViewController(coder: coder)
        }
        tableViewController.tabBarItem = tabBarItem
        tabBarController.viewControllers?.append(tableViewController)
    }
}
