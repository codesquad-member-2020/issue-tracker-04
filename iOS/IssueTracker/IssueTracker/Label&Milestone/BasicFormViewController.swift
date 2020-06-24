import UIKit

class BasicFormViewController: UIViewController {
    @IBOutlet weak var contentView: UIView!

    @IBAction func closeButtonTouched(_ sender: UIButton) {
        self.remove()
    }

    @IBAction func saveButtonTouched(_ sender: UIButton) { }

    @IBAction func clearButtonTouched(_ sender: UIButton) { }
}

class LabelFormViewController: BasicFormViewController {
    var formView: LabelFormView = .loadFromNib()

    override func viewDidLoad() {
        super.viewDidLoad()

        contentView.addSubviewAsSameSize(formView)
    }

    override func clearButtonTouched(_ sender: UIButton) {
        formView.clearAllFields()
    }
}

class MilestoneFormViewController: BasicFormViewController {
    var formView: MilestoneFormView = .loadFromNib()

    override func viewDidLoad() {
        super.viewDidLoad()

        contentView.addSubviewAsSameSize(formView)
    }

    override func clearButtonTouched(_ sender: UIButton) {
        formView.clearAllFields()
    }
}
