import UIKit

class IssueDetailViewController: UIViewController {
    enum IssueInfoState {
        case expanded
        case collapsed
    }

    // MARK: - Property

    @IBOutlet weak var issueDetailView: IssueDetailView!
    @IBOutlet weak var commentTableView: UITableView!
    @IBOutlet weak var issueInfoView: UIView!


    var commentDataSource: TableViewDataSource<CommentCollection>! = nil

    private let issueModelController: IssueModelController
    var issue: Issue { issueModelController.issue }


    var nextState: IssueInfoState {
        issueInfoVisible ? .collapsed : .expanded
    }

    var handleAreaHeight: CGFloat {self.view.frame.height * 0.2}
    var issueInfoVisible = false
    var visualEffectView: UIVisualEffectView!
    var issueInfoViewHeight: CGFloat  {
        self.view.frame.height * 0.8
    }

    init?(coder: NSCoder, issueModelController: IssueModelController) {
        self.issueModelController = issueModelController
        super.init(coder: coder)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    // MARK: - View Cycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupButtonIssueInfoView()
        issueModelController.addObserver(self)
        configureView()
        configureCommentTableView()
    }

    // MARK: - Setup

    private func configureView() {
        let configurator = IssueDetailViewConfigurator()
        configurator.configure(issueDetailView, with: issueModelController.issue)
    }

    func setupButtonIssueInfoView() {
        setupVisualEffectView()
//        setupIssueInfoView()

    }
    
    private func setupVisualEffectView() {
        visualEffectView = UIVisualEffectView(frame: self.view.bounds) // 블러 화면
        self.view.addSubview(visualEffectView)

        visualEffectView.isHidden = true
    }

//    private func setupIssueInfoView() {
//
//        issueInfoView.movePreviousCommentButton.addTarget(self, action: #selector(moveToPreviousComment), for: .touchUpInside)
//        issueInfoView.moveNextCommentButton.addTarget(self, action: #selector(moveToNextComment), for: .touchUpInside)
//    }

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
    private func createAnimation(state:IssueInfoState, duration:TimeInterval) {
        let cardMoveUpAnimation = UIViewPropertyAnimator(duration: duration, curve: .linear) { [weak self] in
            guard let `self` = self else  { return }
            switch state {
            case .collapsed:
                self.issueInfoView.frame.origin.y = self.view.frame.height - self.handleAreaHeight
                
            case .expanded:
                self.issueInfoView.frame.origin.y = self.view.frame.height - self.issueInfoViewHeight
                
            }
        }
        cardMoveUpAnimation.addCompletion { [weak self] _ in
            self?.issueInfoVisible =  state ==  .collapsed ? false : true
        }
        cardMoveUpAnimation.startAnimation()

        createBlurViewAnimation(state: state, duration: duration)

    }

    private func createBlurViewAnimation(state:IssueInfoState, duration:TimeInterval) {
        let visualEffectAnimation = UIViewPropertyAnimator.init(duration: duration, curve: .linear) { [weak self ] in
            switch state {
            case .expanded:
                self?.visualEffectView.effect = UIBlurEffect(style: .dark)

            case .collapsed:
                self?.visualEffectView.effect =  nil
            }
        }
        visualEffectAnimation.startAnimation()
    }


    // MARK: - Navigation

    @IBSegueAction private func showEditIssue(coder: NSCoder) -> IssueFormViewController? {
        IssueFormViewController(coder: coder, state: .edit(issue: issueModelController.issue), delegate: self)
    }

}

class IssueDetailViewConfigurator {
    func configure(_ view: IssueDetailView, with issue: Issue) {
        view.titleLabel.text = issue.title
        view.bodyView.text = issue.body
        view.authorLabel.text = String(describing: issue.owner)
    }
}

extension IssueDetailViewController: IssueFormViewControllerDelegate {
    func issueFormViewControllerDidEdit(_ issue: Issue) {
        issueModelController.update(issue: issue)
    }
}

extension IssueDetailViewController: Observer {
    func ObservingObjectDidUpdate() {
        configureView()
    }
}

// MARK: - CommentTableView

extension IssueDetailViewController {
    private func configureCommentTableView() {
        commentDataSource = .make(for: issue.comments, reuseIdentifier: Identifier.Cell.comment)
        commentTableView.dataSource = commentDataSource
    }

    private func configureCell(comment: CommentCollection.Element, cell: UITableViewCell) {
        guard let cell = cell as? CommentCell else { return }

        cell.bodyLabel.text = "Foooooo"
    }
}
