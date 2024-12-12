//
//  LoginView.swift
//  iosApp
//
//  Created by Ankush Kushwaha on 09/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import Shared

struct LoginView: View {
    
    @StateObject var viewModel = ViewModelWrapper()

    // These binding properties can be shifted to ViewModelWrapper
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var showPassword: Bool = false

    var body: some View {
        
            NavigationStack {
                
                VStack(spacing: 20) {
                    
                    TextField("Email", text: $username)
                        .textContentType(.emailAddress)
                        .keyboardType(.emailAddress)
                        .autocapitalization(.none)
                        .padding()
                        .background(Color(.secondarySystemBackground))
                        .cornerRadius(8)
                    
                    HStack {
                        if showPassword {
                            TextField("Password", text: $password)
                        } else {
                            SecureField("Password", text: $password)
                        }
                        Button(action: {
                            showPassword.toggle()
                        }) {
                            Image(systemName: showPassword ? "eye.slash" : "eye")
                                .foregroundColor(.gray)
                        }
                    }
                    .padding()
                    .background(Color(.secondarySystemBackground))
                    .cornerRadius(8)
                    
                    
                    Button(action: viewModel.startFetch) {
                        if viewModel.state?.isLoading?.boolValue ?? false {
                            ProgressView()
                                .progressViewStyle(CircularProgressViewStyle())
                        } else {
                            Text("Login")
                                .foregroundColor(.white)
                                .padding()
                                .frame(maxWidth: .infinity)
                                .background(Color.blue)
                                .cornerRadius(8)
                        }
                    }
                    .disabled(!(viewModel.state?.isValidCredentials?.boolValue ?? false))
                    .opacity(viewModel.state?.isValidCredentials?.boolValue ?? false ? 1 : 0.5)
                    
                    ChildView(name: $username)
                }
                .padding()
                
                .navigationDestination(isPresented: $viewModel.showDetailView) {
                    ContentView()
                }
            }

            .onChange(of: username) { _ in
                updateViewModel()
            }
            .onChange(of: password) { _ in
                updateViewModel()
            }
            .onChange(of: viewModel.showDetailView) { _ in
                updateViewModel()
            }
    }
    
    private func updateViewModel() {
        print("updateViewModel")
        
        let state = ViewState(
            username: username,
            password: password,
            isLoading: false,
            errorMessage: viewModel.state?.errorMessage,
            isValidCredentials: viewModel.state?.isValidCredentials,
            isRememberLoginSelected: viewModel.state?.isRememberLoginSelected,
            pushToAboutScreen: viewModel.state?.pushToAboutScreen,
            pushToHomeScreen: viewModel.showDetailView ? true : false,
            accessToken: viewModel.state?.accessToken
        )
        viewModel.updateState(newState: state)
    }
}

struct ChildView: View {
    @Binding var name: String

    var body: some View {
        TextField("Enter your email", text: $name)
            .textFieldStyle(RoundedBorderTextFieldStyle())
            .padding()
    }
}


