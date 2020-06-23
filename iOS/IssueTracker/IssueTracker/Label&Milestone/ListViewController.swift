import UIKit

class ListViewController: UIViewController {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var addButton: UIButton!

    override func viewDidLoad() {
        super.viewDidLoad()
    }
}

class LabelListViewController: ListViewController {
    override func viewDidLoad() {
        super.viewDidLoad()

        titleLabel.text = "Label"

        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let vc = storyboard.instantiateViewController(identifier: "BasicFormViewController") { coder in
            LabelFormViewController.init(coder: coder)
        }
        add(vc)
    }
}

class MilestoneListViewController: ListViewController {
    override func viewDidLoad() {
        super.viewDidLoad()


        titleLabel.text = "Milestone"

        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let vc = storyboard.instantiateViewController(identifier: "BasicFormViewController") { coder in
            MilestoneFormViewController.init(coder: coder)
        }
        add(vc)
    }
}
