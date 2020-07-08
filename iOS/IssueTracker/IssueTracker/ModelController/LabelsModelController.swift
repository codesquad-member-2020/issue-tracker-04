import Foundation

class LabelsModelController {
    private(set) var labels: [Label]

    init(labels: [Label] = []) {
        self.labels = labels
    }
}
