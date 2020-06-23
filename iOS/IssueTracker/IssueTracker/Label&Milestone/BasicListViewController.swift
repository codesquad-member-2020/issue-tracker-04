import UIKit

class BasicListViewController: UIViewController {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var addButton: UIButton!
    @IBOutlet weak var containerView: UIView!

    private(set) var titleLabelText: String = ""

    override func viewDidLoad() {
        super.viewDidLoad()

        titleLabel.text = titleLabelText
    }

    func addFormViewController(_ type: BasicFormViewController.Type) {
        let vc = type.instantiateFromStoryboard(identifier: Identifier.ViewController.basicForm)
        add(vc, inView: containerView)
    }

    // MARK: - IBAction

    @IBAction func addButtonTouched(_ sender: UIButton) { }
}

class LabelListViewController: BasicListViewController {
    override var titleLabelText: String { Name.Title.labelList }

    override func addButtonTouched(_ sender: UIButton) {
        addFormViewController(LabelFormViewController.self)
    }
}

class MilestoneListViewController: BasicListViewController {
    override var titleLabelText: String { Name.Title.milestoneList }

    override func addButtonTouched(_ sender: UIButton) {
        addFormViewController(MilestoneFormViewController.self)
    }
}
