import UIKit

class SectionTitleLabel: UILabel {
    static let fontSize: CGFloat = 26.0

    override func awakeFromNib() {
        super.awakeFromNib()

        configure()
    }

    private func configure() {
        self.font = .systemFont(ofSize: Self.fontSize, weight: .bold)
    }
}
