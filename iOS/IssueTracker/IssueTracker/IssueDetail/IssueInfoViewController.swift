import UIKit

class IssueInfoViewController: UIViewController {

    
    @IBOutlet weak var handlerArea: UIButton!
    @IBOutlet weak var addComment: UIButton!
    @IBOutlet weak var moveBeforeCommentButton: UIButton!
    @IBOutlet weak var moveNextCommentButton: UIButton!

    override func viewDidLoad() {
        super.viewDidLoad()
        setupIssueInfoView()
    }
    
    private func setupIssueInfoView() {
        self.view.clipsToBounds = true
        
        self.view.layer.cornerRadius = 15
        
        let panGestureRecognizer = UIPanGestureRecognizer(target: self, action: #selector(IssueDetailViewController.gestureStarted))
        self.handlerArea.addGestureRecognizer(panGestureRecognizer)
    }

}
