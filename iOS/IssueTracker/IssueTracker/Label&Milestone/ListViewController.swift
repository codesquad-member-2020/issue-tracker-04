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
    }
}

class MilestoneListViewController: ListViewController {
    override func viewDidLoad() {
        super.viewDidLoad()


        titleLabel.text = "Milestone"
    }
}
