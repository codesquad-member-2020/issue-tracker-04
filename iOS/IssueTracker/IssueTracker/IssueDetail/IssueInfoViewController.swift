import UIKit

class IssueInfoViewController: UIViewController {

    
    @IBOutlet weak var handlerArea: UIButton!
    @IBOutlet weak var addComment: UIButton!
    @IBOutlet weak var movePreviousCommentButton: UIButton!
    @IBOutlet weak var moveNextCommentButton: UIButton!

    override func viewDidLoad() {
        super.viewDidLoad()
        setupIssueInfoView()
    }
    
    private func setupIssueInfoView() {
        
        let panGestureRecognizer = UIPanGestureRecognizer(target: self, action: #selector(IssueDetailViewController.gestureStarted))
        self.handlerArea.addGestureRecognizer(panGestureRecognizer)
    }

}
