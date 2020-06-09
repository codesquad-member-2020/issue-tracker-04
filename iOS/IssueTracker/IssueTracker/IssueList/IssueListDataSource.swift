import UIKit

class IssueListDataSource: NSObject {
    
    private var issueList: IssueCollection
    
    init(_ issueList: IssueCollection = .init()) {
        self.issueList = issueList
    }
    
}


extension IssueListDataSource: UITableViewDataSource {
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

}
