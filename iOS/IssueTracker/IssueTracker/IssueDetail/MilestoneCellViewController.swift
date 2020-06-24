import UIKit

class MilestoneCellViewController: UIViewController {

    @IBOutlet weak var progressIndicator: UIProgressView!
    @IBOutlet weak var titleLabel: UILabel!
    var progress: Float = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupProgressView()
    }

    private func setupProgressView() {
        progressIndicator.progress = 0.7
        progressIndicator.setProgress(progressIndicator.progress, animated: true)
    }
    
}
