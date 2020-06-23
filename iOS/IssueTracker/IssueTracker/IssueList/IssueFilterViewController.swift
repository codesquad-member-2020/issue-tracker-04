import UIKit

class IssueFilterViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func cancelTouched(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }

}
