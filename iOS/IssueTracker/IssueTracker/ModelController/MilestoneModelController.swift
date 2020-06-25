import Foundation

class MilestoneModelController {
    private(set) var milestones: [Milestone]

    init(milestones: [Milestone] = []) {
        self.milestones = milestones
    }
}
