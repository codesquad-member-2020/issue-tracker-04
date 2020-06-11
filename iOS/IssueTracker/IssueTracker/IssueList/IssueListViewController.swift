import UIKit

class IssueListViewController: UIViewController {
    
    private var dataSource: IssueListDataSource = .init()
    @IBOutlet weak var editButton: UIButton!
    
    @IBOutlet weak var tableView: UITableView!

    override func viewDidLoad() {
        super.viewDidLoad()

        setupTableView()
    }
    
    
    
    private func setupTableView() {
        let user = User(id: 1, name: "모오오오스")
        let issueList: IssueCollection = [Issue(id: 1, title: "title1", body: nil, owner: user),Issue(id: 2, title: "title2", body: "Something", owner: user),Issue(id: 3, title: "title3", body: "Special", owner: user)]
        self.dataSource = IssueListDataSource(issueList)
        self.tableView.dataSource = dataSource
        self.tableView.delegate = self
        tableView.isEditing = false

    }
    
    @IBAction func startEditing(_ sender: UIButton) {
        self.tableView.allowsMultipleSelectionDuringEditing = true
        let isEditing = tableView.isEditing
            tableView.setEditing(!isEditing, animated: true)
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        return !tableView.isEditing
    }

}

extension IssueListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        let share = UIContextualAction(style: .normal, title: "Share") { action, view, completion in
            guard let dataSource = tableView.dataSource as? IssueListDataSource else { return }
            dataSource.closeIssue(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
            debugPrint("Share")
            completion(true)
        }

        let delete = UIContextualAction(style: .destructive, title: "Delete") { action, view, completion in
            guard let dataSource = tableView.dataSource as? IssueListDataSource else { return }
            dataSource.removeIssue(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
            debugPrint("Delete")
            completion(true)
        }

        share.backgroundColor = .systemGreen
        share.image = UIImage(systemName: SystemImageName.cellShare)
        delete.image = UIImage(systemName: SystemImageName.cellDelete)

        let swipeAction = UISwipeActionsConfiguration(actions: [share, delete])

        return swipeAction
    }
    
    func tableView(_ tableView: UITableView, editingStyleForRowAt indexPath: IndexPath) -> UITableViewCell.EditingStyle {
        return .none
    }

    func tableView(_ tableView: UITableView, shouldIndentWhileEditingRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    
}

enum SystemImageName {
    static let cellShare = "checkmark.rectangle"
    static let cellDelete = "trash"
}
