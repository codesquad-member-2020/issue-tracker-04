import Foundation

struct Milestone: Model {
    let id: ID
    let title: String
    let dueDate: String
    let description: String
}

struct MilestoneWrapper {
    var allData: [Milestone]
}
