import UIKit

class MainTabBarController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()

        let labelItem = UITabBarItem(title: "Label", image: UIImage(systemName: "tag.fill"), tag: 1)
        let milestoneItem = UITabBarItem(title: "Milestone", image: UIImage(systemName: "calendar.circle.fill"), tag: 2)


        let labelVC = TabBarControllerAdder<LabelListViewController>(title: "Label", tabBarItem: labelItem)
        let milestoneVC = TabBarControllerAdder<MilestoneListViewController>(title: "Milestone", tabBarItem: milestoneItem)
        labelVC.add(in: self)
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
