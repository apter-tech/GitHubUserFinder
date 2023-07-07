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
final class ReducerViewModel<S, V>: ObservableObject where S: UiState, V: ViewModel, V: StateFlowAdaptable {
    @LazyKoin var viewModel: V

    @Published public var state: S?
    @Published public var error: String?
    private var cancellables = Set<AnyCancellable>()

    init() {
        doPublish(viewModel.stateFlowAdapter) { [weak self] in
            self?.state = $0 as? S
        }
        .store(in: &cancellables)

        doPublish(viewModel.errorFlowAdapter) { [weak self] in
            if let error = $0 as? String {
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
