import Foundation

struct Label: Model {
    let id: ID
    let title: String
//    let color: String
}

struct LabelWrapper: Codable {
    let allData: [Label]
}
