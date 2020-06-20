import UIKit

class IssueListDataSource: NSObject {

    private var issueList: IssueCollection

    init(_ issueList: IssueCollection = .init()) {
        self.issueList = issueList
    }

    func closeIssue(at index: IssueCollection.Index) {
        // TODO: need to implement -> change issue status from open to closed
        issueList.remove(at: index)
    }

    func removeIssue(at index: IssueCollection.Index) {
        issueList.remove(at: index)
    }

    func add(issue: Issue) {
        issueList.add(issue)
    }

    func issue(at index: Int) -> Issue {
        return issueList[index]
    }

    func updateList(_ issueList: IssueCollection) {
        self.issueList = issueList
    }
}

extension IssueListDataSource: UITableViewDataSource {

    // TODO: show open issues in issueList using filter(by state:) method
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return issueList.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        guard let cell = tableView.dequeueReusableCell(withIdentifier: IssueCell.identifier, for: indexPath) as? IssueCell else { return IssueCell() }

        let issue = self.issueList[indexPath.row]

        cell.titleLabel.text = issue.title
        cell.detailLabel.text = issue.body

        return cell
    }

    func tableView(_ tableView: UITableView, moveRowAt sourceIndexPath: IndexPath, to destinationIndexPath: IndexPath) {
        self.issueList.remove(at: sourceIndexPath.row)
        self.issueList.insert(contentsOf: issueList, at: destinationIndexPath.row)
        debugPrint("\(sourceIndexPath.row) => \(destinationIndexPath.row)")
    }

    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        guard tableView.isEditing else { return false }
        return true
    }

    func tableView(_ tableView: UITableView, editingStyleForRowAt indexPath: IndexPath) -> UITableViewCell.EditingStyle {
        return .none
    }

}
