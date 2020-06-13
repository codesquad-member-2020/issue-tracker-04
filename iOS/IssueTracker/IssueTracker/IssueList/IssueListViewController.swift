import UIKit

class IssueListViewController: UIViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    private var dataSource: IssueListDataSource = .init()
    
    var cancelBarButton: UIBarButtonItem  {
        return UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(didCancelButtonPressed))
    }
    
    var editBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Edit", style: .plain, target: self, action: #selector(didEditButtonPressed))
    }
    
    var filterBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Filter", style: .plain, target: self, action: #selector(didFilterButtonPressed))
    }
    
    var selectAllBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Select All", style: .plain, target: self, action: #selector(didSelectAllButtonPressed))
    }
    
    var deselectAllBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Deselect All", style: .plain, target: self, action: #selector(didDeselectAllButtonPressed))
    }
    
    override var isEditing: Bool {
        didSet {
            setupButtons()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupTableView()
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        return !tableView.isEditing
    }
    
    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        tableView.setEditing(editing, animated: true)
    }
    
    @IBAction func closeButtonTapped(_ sender: Any) {
        tableView.indexPathsForSelectedRows?.forEach{
            dataSource.closeIssue(at: $0.row)
        }
        isEditing = !isEditing
        tableView.allowsMultipleSelectionDuringEditing = false
    }
    
    private func setupTableView() {
        let user = User(id: 1, name: "모오오오스")
        let issueList: IssueCollection = [Issue(id: 1, title: "title1", body: nil, owner: user),Issue(id: 2, title: "title2", body: "Something", owner: user),Issue(id: 3, title: "title3", body: "Special", owner: user)]
        self.dataSource = IssueListDataSource(issueList)
        self.tableView.dataSource = dataSource
        self.tableView.delegate = self
        tableView.allowsMultipleSelectionDuringEditing = true
        self.navigationItem.rightBarButtonItem = editBarButton
    }
    
    private func setupButtons() {
        switch isEditing {
        case true: self.navigationItem.rightBarButtonItem =
        cancelBarButton
        self.navigationItem.leftBarButtonItem = selectAllBarButton
            
        case false:
            self.navigationItem.rightBarButtonItem = editBarButton
            self.navigationItem.leftBarButtonItem = filterBarButton
        }
    }
    
    @objc private func didCancelButtonPressed() {
        isEditing = false
        tableView.allowsMultipleSelectionDuringEditing = false
    }
    
    
    @objc private func didEditButtonPressed() {
        tableView.allowsMultipleSelectionDuringEditing = true
        isEditing = true
        tableView.allowsMultipleSelectionDuringEditing = true
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        return !tableView.isEditing
    }
    
    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        tableView.setEditing(editing, animated: true)
    }

    @IBAction func closeButtonTapped(_ sender: Any) {
         tableView.indexPathsForSelectedRows?.forEach{
             self.closedIssuesIndexPath.append($0)
         }
         isEditing = !isEditing
         tableView.allowsMultipleSelectionDuringEditing = false
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
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .none {
            closedIssuesIndexPath.remove(at: indexPath.row)
            tableView.reloadData()
        }
    }
    
}

enum SystemImageName {
    static let cellShare = "checkmark.rectangle"
    static let cellDelete = "trash"
}
