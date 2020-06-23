import UIKit

class BasicFormViewController: UIViewController {
    @IBOutlet weak var contentView: UIView!

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func closeButtonTouched(_ sender: UIButton) {
        self.remove()
    }

    @IBAction func saveButtonTouched(_ sender: UIButton) {
        debugPrint(#function)
    }

    @IBAction func clearButtonTouched(_ sender: UIButton) {
        debugPrint(#function)
    }
}

class LabelFormViewController: BasicFormViewController {
    override func viewDidLoad() {
        super.viewDidLoad()

        let nib = UINib(nibName: "LabelFormView", bundle: .main)
        let view = nib.instantiate(withOwner: self, options: .none).first as! LabelFormView
        view.frame = contentView.bounds
        contentView.addSubview(view)
    }
}
class MilestoneFormViewController: BasicFormViewController {
    override func viewDidLoad() {
        super.viewDidLoad()

        let nib = UINib(nibName: "MilestoneFormView", bundle: .main)
        let view = nib.instantiate(withOwner: self, options: .none).first as! MilestoneFormView
        view.frame = contentView.bounds
        contentView.addSubview(view)
    }
}
