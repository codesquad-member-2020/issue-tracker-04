//
//  IssueListTableViewDelegate.swift
//  IssueTracker
//
//  Created by Keunna Lee on 2020/06/17.
//  Copyright © 2020 Codesquad. All rights reserved.
//

import UIKit

class IssueListTableViewDelegate: NSObject, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        let close = UIContextualAction(style: .normal, title: "Close") { action, view, completion in
            guard let dataSource = tableView.dataSource as? IssueListDataSource else { return }
            dataSource.closeIssue(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
            debugPrint("Close")
            completion(true)
        }
        
        let delete = UIContextualAction(style: .destructive, title: "Delete") { action, view, completion in
            guard let dataSource = tableView.dataSource as? IssueListDataSource else { return }
            dataSource.removeIssue(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
            debugPrint("Delete")
            completion(true)
        }

        close.backgroundColor = .systemGreen
        close.image = UIImage(systemName: SystemImageName.cellClose)
        delete.image = UIImage(systemName: SystemImageName.cellDelete)

        let swipeAction = UISwipeActionsConfiguration(actions: [close, delete])

        return swipeAction
    }
    
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

}
//extension IssueListTableViewDelegate: IssueStateDelegate {
//    func changeState(to state: IssueListState) {
//
//    }
//
//}
