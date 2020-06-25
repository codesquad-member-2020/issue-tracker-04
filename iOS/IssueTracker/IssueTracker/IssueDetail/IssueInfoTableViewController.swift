import UIKit

class IssueInfoTableViewController: UITableViewController {

    @IBOutlet weak var assigneeView: UIView!
    @IBOutlet weak var labelView: UIView!
    @IBOutlet weak var milestoneView: UIView!
    var issueModelController: IssueModelController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
         self.clearsSelectionOnViewWillAppear = false
        setupAssigneeView()
        setupLabelView()
        setupMilestoneView()
    }
    
    private func setupAssigneeView() {
        let assigneeVC = storyboard?.instantiateViewController(identifier: Identifier.ViewController.assigneeCellVC) {
            AssigneeCellViewController(coder: $0)
        }
        assigneeVC?.assignees = issueModelController?.issue.assignees 
        addChild(assigneeVC!)
        assigneeView.addSubview(assigneeVC!.view)
        assigneeVC!.view.frame = assigneeView.bounds
        assigneeVC!.didMove(toParent: self)
    }
    
    private func setupLabelView() {
        guard let labelVC = storyboard?.instantiateViewController(withIdentifier: Identifier.ViewController.labelCellVC) else { return }
        addChild(labelVC)
        labelView.addSubview(labelVC.view)
        labelVC.view.frame = labelView.bounds
        labelVC.didMove(toParent: self)
    }
    
    private func setupMilestoneView() {
        guard let milestoneVC = storyboard?.instantiateViewController(withIdentifier: Identifier.ViewController.milestoneCellVC) else { return }
        addChild(milestoneVC)
        milestoneView.addSubview(milestoneVC.view)
        milestoneVC.view.frame = milestoneView.bounds
        milestoneVC.didMove(toParent: self)
    }

}
