import UIKit

class IssueListTableViewDelegate: NSObject, UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard let tableView = tableView as? IssueListTableView else { return }
        if case .edit(select: _) = tableView.issueState, isSelectedRows(tableView) {
            tableView.titleLabel.text = tableView.titleWhenEditing()
            tableView.issueState = .edit(select: .some)
        }
    }
        
    func tableView(_ tableView: UITableView, didDeselectRowAt indexPath: IndexPath) {
        guard let tableView = tableView as? IssueListTableView else { return }
        tableView.titleLabel.text = tableView.titleWhenEditing()
        if !isSelectedRows(tableView) {
            tableView.issueState = .edit(select: .none)
        }
    }
    
    func isSelectedRows(_ tableView: UITableView) -> Bool {
        !(tableView.indexPathsForSelectedRows?.isEmpty ?? true)
    }

    func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        guard let dataSource = tableView.dataSource as? IssueListDataSource else { return nil }

        func didShare() {
            dataSource.closeIssue(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
        }
        func didDelete() {
            dataSource.removeIssue(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
        }

        let share = ActionType.make(.share, handler: didShare)
        let delete = ActionType.make(.delete, handler: didDelete)

        return UISwipeActionsConfiguration(actions: [share, delete])
    }

}

extension IssueListTableViewDelegate {
    enum ActionType {
        case share
        case delete

        struct Attribute {
            let style: UIContextualAction.Style
            let title: String?
            let image: UIImage?
            let backgroundColor: UIColor?
        }

        static func make(_ actionType: ActionType, handler: @escaping () -> Void) -> UIContextualAction {
            let type = actionType.attribute
            let action = UIContextualAction(style: type.style, title: type.title) { action, view, completion in
                handler()
                completion(true)
            }

            action.backgroundColor = type.backgroundColor
            action.image = type.image

            return action
        }

        var attribute: Attribute {
            switch self {
            case .share:
                let image = UIImage(systemName: SystemImageName.cellClose)
                return Attribute(style: .normal, title: "Share", image: image, backgroundColor: .systemGreen)
            case .delete:
                let image = UIImage(systemName: SystemImageName.cellDelete)
                return Attribute(style: .destructive, title: "Delete", image: image, backgroundColor: nil)
            }
        }
    }
}
