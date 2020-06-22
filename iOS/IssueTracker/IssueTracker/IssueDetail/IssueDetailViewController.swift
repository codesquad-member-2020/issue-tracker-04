import UIKit

class IssueDetailViewController: UIViewController {
    
    enum IssueInfoState {
        case expanded
        case collpased
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var bodyView: UITextView!
    @IBOutlet weak var ownerLabel: UILabel!
    @IBOutlet weak var issueInfoView: UIView!
    
    
    var issue: Issue
    var handleAreaHeight:CGFloat {self.view.frame.height * 0.2}
    var issueInfoVisible = false
    var visualEffectView: UIVisualEffectView!
    var issueInfoViewHeight:CGFloat  {
        self.view.frame.height * 0.8
    }
    var nextState:IssueInfoState {
        issueInfoVisible ? .collpased : .expanded
    }
    
    
    init?(coder: NSCoder, issue: Issue) {
        self.issue = issue
        super.init(coder: coder)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        titleLabel.text = issue.title
        bodyView.text = issue.body
        ownerLabel.text = String(describing: issue.owner)
        setupVisualEffectView()
        
//        self.issueInfoView.movePreviousCommentButton.addTarget(self, action: #selector(moveToPreviousComment), for: .touchUpInside)
//        issueInfoView.moveNextCommentButton.addTarget(self, action: #selector(moveToNextComment), for: .touchUpInside)
    }
    
    private func setupVisualEffectView() {
        visualEffectView = UIVisualEffectView(frame: self.view.bounds) // 블러 화면
        self.view.addSubview(visualEffectView)
        
        visualEffectView.isHidden = true
    }
    
    // MARK: - Selector
    
    @objc func moveToPreviousComment() {
        print(#function)
        //        let currentIndexPath = tableView.indexPathsForVisibleRows?.first ?? IndexPath(row: 1, section: 0)
        //        tableView.scrollToRow(at: IndexPath(row: currentIndexPath.row - 1 , section: currentIndexPath.section), at: .top, animated: true)
    }
    
    @objc func moveToNextComment() {
        print(#function)
        //        let currentIndexPath = tableView.indexPathsForVisibleRows?.first ?? IndexPath(row: 0, section: 0)
        //        tableView.scrollToRow(at: IndexPath(row: currentIndexPath.row + 1 , section: currentIndexPath.section), at: .top, animated: true)
    }
    
    @objc func gestureStarted(_ recognizer:UIPanGestureRecognizer) {
        self.visualEffectView.isHidden = false
        self.view.bringSubviewToFront(issueInfoView)
        createAnimation(state: nextState, duration: 0.5)
    }
    
    // MARK: - Animation
    // 옮겨보기
    private func createAnimation(state:IssueInfoState, duration:TimeInterval) {
        let cardMoveUpAnimation = UIViewPropertyAnimator(duration: duration, curve: .linear) { [weak self] in
            guard let `self` = self else  { return }
            switch state {
            case .collpased:
                self.issueInfoView.frame.origin.y = self.view.frame.height - self.handleAreaHeight
                
            case .expanded:
                self.issueInfoView.frame.origin.y = self.view.frame.height - self.issueInfoViewHeight
                
            }
        }
        cardMoveUpAnimation.addCompletion { [weak self] _ in
            self?.issueInfoVisible =  state ==  .collpased ? false : true
        }
        cardMoveUpAnimation.startAnimation()
        
        createBlurViewAnimation(state: state, duration: duration)
        
    }
    
    private func createBlurViewAnimation(state:IssueInfoState, duration:TimeInterval) {
        let visualEffectAnimation = UIViewPropertyAnimator.init(duration: duration, curve: .linear) { [weak self ] in
            switch state {
            case .expanded:
                self?.visualEffectView.effect = UIBlurEffect(style: .dark)
                
            case .collpased:
                self?.visualEffectView.effect =  nil
            }
        }
        visualEffectAnimation.startAnimation()
    }
    
    
    // MARK: - Navigation
    
    @IBSegueAction
    private func showEditIssue(coder: NSCoder) -> IssueFormViewController? {
        return IssueFormViewController(coder: coder, issue: issue)
    }
    
}
