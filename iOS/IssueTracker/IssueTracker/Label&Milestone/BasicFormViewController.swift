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

class LabelFormViewController: BasicFormViewController { }
class MilestoneFormViewController: BasicFormViewController { }
