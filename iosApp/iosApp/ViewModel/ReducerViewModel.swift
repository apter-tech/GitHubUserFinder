//
//  GitHubUserListViewModelImpl.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine

@MainActor
class ReducerViewModel<S: UiState, V: ViewModel>: ObservableObject {
    var viewModel: V

    @Published public var state: S?
    var cancellables = Set<AnyCancellable>()

    init(viewModel: V) {
        self.viewModel = viewModel

        guard let reducer = (viewModel as? Reducer<S, UiEvent>) else { return }

        doPublish(reducer.stateFlowAdapter) { [weak self] in
            self?.state = $0
        }
        .store(in: &cancellables)
    }

    func deactivate() {
        viewModel.clear()
        cancellables.removeAll()
    }
}
