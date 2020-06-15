import UIKit

class IssueDetailViewController: UIViewController {
    @IBOutlet weak var issueDetailView: IssueDetailView!

    var issue: Issue

    init?(coder: NSCoder, issue: Issue) {
        self.issue = issue
        super.init(coder: coder)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

    }

    // MARK: - Navigation

    @IBSegueAction
    private func showEditIssue(coder: NSCoder) -> IssueFormViewController? {
        return IssueFormViewController(coder: coder, issue: issue)
    }

    private func configure(view: IssueDetailView) {
        view.titleLabel.text = issue.title
        view.bodyView.text = issue.body
        view.ownerLabel.text = String(describing: issue.owner)
    }

}

class IssueDetailViewConfigurator {
//    func configure(_ view: )
}
