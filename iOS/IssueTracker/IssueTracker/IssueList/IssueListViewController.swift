import UIKit

class IssueListViewController: UIViewController {
    
    enum State {
        case normal, editing, selected
    }
    
    private var dataSource: IssueListDataSource = .init()
    private var tableViewState: State = .normal {
        didSet {
            buttonSettingByState()
        }
    }
    
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
        buttonSettingByState()
    }
    
    private func buttonSettingByState() {
        switch tableViewState {
        case .normal:
            navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Edit", style: .plain, target: self, action: #selector(editTapped))
            navigationItem.leftBarButtonItem = UIBarButtonItem(title: "Filter", style: .plain, target: self, action: #selector(filterTapped))


        case .editing:
            setEditingModeRightButtons()
            navigationItem.leftBarButtonItem = UIBarButtonItem(title: "Select All", style: .plain, target: self, action: #selector(selectAllTapped))

        case .selected:
            setEditingModeRightButtons()
            navigationItem.leftBarButtonItem = UIBarButtonItem(title: "Deselect All", style: .plain, target: self, action: #selector(deselectAllTapped))

        }
    }
    
    private func setEditingModeRightButtons() {
        self.tableView.allowsMultipleSelectionDuringEditing = true
        let isEditing = tableView.isEditing
            tableView.setEditing(!isEditing, animated: true)
        tableViewState = .editing
        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Cancel", style: .done, target: self, action: #selector(cancelTapped))
    }
    
    @objc func filterTapped() {
        debugPrint(#function)
    }

    @objc func selectAllTapped() {
        debugPrint(#function)
    }
    
    @objc func deselectAllTapped() {
        debugPrint(#function)
    }
    
    @objc func editTapped() {
        self.tableView.allowsMultipleSelectionDuringEditing = true
        let isEditing = tableView.isEditing
            tableView.setEditing(!isEditing, animated: true)
        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Cancel", style: .done, target: self, action: #selector(cancelTapped))
    }
    
    @objc func cancelTapped() {
        self.tableView.allowsMultipleSelectionDuringEditing = false
        let isEditing = tableView.isEditing
            tableView.setEditing(!isEditing, animated: false)
        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Edit", style: .plain, target: self, action: #selector(editTapped))
    }

    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        return !tableView.isEditing
    }
    
    @IBAction func editButtonTapped(_ sender: Any) {
        tableViewState = .editing
        
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
