import Foundation

class IssueStateController {
    
    let user: User
    let issue: Issue
    let issueList: IssueCollection
 
    init(user: User, issue: Issue, issueList: IssueCollection = .init()) {
        self.user = user
        self.issue = issue
        self.issueList = issueList
    }
    
    var openIssues: IssueCollection {
        return issueList.filter(by: .open)
    }
    
    var closedIssues: IssueCollection {
        return issueList.filter(by: .closed)
    }

    var authoredIssues: IssueCollection {
        return openIssues.filter(by: self.user)

    }
    
    var assignedIssues: IssueCollection {
        return openIssues.filter(contains: self.user)
    }
    
    var commentedIssues: IssueCollection {
        return openIssues
            .filter{$0.comments.contains(author: user)}
    }

}
