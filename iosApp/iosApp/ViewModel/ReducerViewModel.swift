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
final class ReducerViewModel<S: UiState, V: ViewModel>: ObservableObject {
    @LazyKoin var viewModel: V

    @Published public var state: S?
    @Published public var error: String?
    private var cancellables = Set<AnyCancellable>()

    init() {
        guard let reducer = (viewModel as? Reducer<S, shared.UiEvent>) else {
            return
        }

        doPublish(reducer.stateFlowAdapter) { [weak self] in
            self?.state = $0
        }
        .store(in: &cancellables)

        doPublish(reducer.errorFlowAdapter) { [weak self] in
            if let error = $0 {
                self?.error = String(error)
            } else {
                self?.error = nil
            }
        }
        .store(in: &cancellables)
    }

    func deactivate() {
        viewModel.clear()
        cancellables.removeAll()
    }
}
