import UIKit

class IssueDetailViewController: UIViewController {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var bodyView: UITextView!
    @IBOutlet weak var ownerLabel: UILabel!

    lazy var issue: Issue = .init(id: 1, title: "첫 번째 이슈", body: "이슈 한 바퀴 \n이슈 목록 만들기", owner: User(id: 1, name: "Jane Doe"))

    override func viewDidLoad() {
        super.viewDidLoad()

        titleLabel.text = issue.title
        bodyView.text = issue.body
        ownerLabel.text = String(describing: issue.owner)
    }

}
