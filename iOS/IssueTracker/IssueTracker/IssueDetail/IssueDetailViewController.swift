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

        let configurator = IssueDetailViewConfigurator()
        configurator.configure(issueDetailView, with: issue)
    }

    // MARK: - Navigation

    @IBSegueAction
    private func showEditIssue(coder: NSCoder) -> IssueFormViewController? {
        return IssueFormViewController(coder: coder, issue: issue)
    }

}

class IssueDetailViewConfigurator {
    func configure(_ view: IssueDetailView, with issue: Issue) {
        view.titleLabel.text = issue.title
        view.bodyView.text = issue.body
        view.authorLabel.text = String(describing: issue.owner)
    }
}
