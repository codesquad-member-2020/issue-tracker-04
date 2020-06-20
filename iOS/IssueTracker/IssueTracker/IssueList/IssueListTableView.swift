import UIKit

class IssueListTableView: UITableView {
    
    weak var issueStateDelegate: IssueStateDelegate?
    var issueState: IssueListState = .normal {
        didSet {
            issueStateDelegate?.stateDidChange(to: issueState)
        }
    }
    @IBOutlet weak var titleLabel: UILabel!
    
    func titleWhenEditing() -> String {
        "\(self.indexPathsForSelectedRows?.count ?? 0)개 선택"
    }
    
    func selectAllRowsWhenEdit() {
        for row in 0..<self.numberOfRows(inSection: 0) {
            self.selectRow(at: IndexPath(row: row, section: 0), animated: true, scrollPosition: .none)
        }
        titleLabel.text = self.titleWhenEditing()
    }
    
    func deselectAllRowsWhenSelected() {
        for row in 0 ..< self.numberOfRows(inSection: 0) {
            self.deselectRow(at: IndexPath(row: row, section: 0), animated: true)
        }
        titleLabel.text = self.titleWhenEditing()
        issueState = .edit(select: .none)
    }
    
    func closeIssuesWhenSelected() {
        self.indexPathsForSelectedRows?.forEach{
            guard let dataSource = self.dataSource as? IssueListDataSource else { return }
            dataSource.closeIssue(at: $0.row)
        }
    }
    
}
