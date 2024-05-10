import tkinter as tk
from tkinter import messagebox, ttk
from tkinter.font import Font

class PDA:
    def __init__(self, states, input_alphabet, stack_alphabet, start_state, start_stack, accept_states, transitions):
        self.states = states
        self.input_alphabet = input_alphabet
        self.stack_alphabet = stack_alphabet
        self.current_state = start_state
        self.start_state = start_state
        self.start_stack = start_stack
        self.stack = [start_stack]
        self.accept_states = accept_states
        self.transitions = transitions

    def reset(self):
        self.current_state = self.start_state
        self.stack = [self.start_stack]

    def transition(self, input_symbol, is_epsilon=False):
        if not self.stack:
            raise ValueError("Error: Attempt to access or pop from an empty stack")

        current_top_of_stack = self.stack[-1] if self.stack else None
        key = (self.current_state, input_symbol, current_top_of_stack)

        # Check if the transition is defined
        if key in self.transitions:
            new_state, new_stack = self.transitions[key][0]
            self.current_state = new_state
            if self.stack:  # Ensure there is something to pop
                self.stack.pop()
            self.stack.extend(reversed(new_stack))  # Push new symbols to the stack
            self.process_epsilon_transitions()
        else:
            # No valid transition found, handle as a rejection if not checking epsilon transitions
            if not is_epsilon:
                self.current_state = 'reject_state'  # Ensure 'reject_state' is treated as a non-accepting state
                return

    def process_epsilon_transitions(self):
        # Continuously process epsilon transitions until none are applicable
        while True:
            epsilon_key = (self.current_state, '', self.stack[-1] if self.stack else None)
            if epsilon_key in self.transitions:
                self.transition('', is_epsilon=True)
            else:
                break

    def process_input(self, input_string):
        for char in input_string:
            if char not in self.input_alphabet and char != '':
                raise ValueError(f"Invalid input symbol: {char}")
            self.transition(char)

        # Final check for epsilon transitions after all input has been processed
            self.process_epsilon_transitions()
            

    def is_accepted(self):
        return self.current_state in self.accept_states and not self.stack

class PDAApp:
    def __init__(self, master, pdas):
        self.master = master
        master.title("Pushdown Automata Simulator")
        master.geometry("400x300")  # Set the size of the window

        # Define font
        self.font = Font(family="Helvetica", size=12)

        self.pdas = pdas
        self.pda = None  # Current PDA

        self.label = tk.Label(master, text="Enter a string:", font=self.font)
        self.label.pack(pady=10)

        self.entry = tk.Entry(master, font=self.font)
        self.entry.pack(pady=10)

        self.pda_selector = ttk.Combobox(master, values=list(self.pdas.keys()), state="readonly", font=self.font)
        self.pda_selector.pack(pady=10)
        self.pda_selector.bind("<<ComboboxSelected>>", self.select_pda)

        self.process_button = tk.Button(master, text="Process", command=self.process, font=self.font)
        self.process_button.pack(pady=10)

        self.result_label = tk.Label(master, text="", font=self.font)
        self.result_label.pack(pady=10)

    def select_pda(self, event):
        pda_name = self.pda_selector.get()
        self.pda = self.pdas[pda_name]

    def process(self):
        input_string = self.entry.get()
        if self.pda:
            self.pda.reset()
            try:
                self.pda.process_input(input_string)
                result = "Accepted" if self.pda.is_accepted() else "Rejected"
                self.result_label.config(text=result)
            except ValueError as e:
                messagebox.showerror("Error", str(e))
        else:
            messagebox.showinfo("Info", "Please select a PDA first.")

root = tk.Tk()
pdas = {
    "Matching Numbers of 0s and 1s": PDA(
        states={'q1', 'q2'},
        input_alphabet={'0', '1'},
        stack_alphabet={'L', '#'},
        start_state='q1',
        start_stack='#',
        accept_states={'q2'},
        transitions={
            ('q1', '0', '#'): [('q1', ['L', '#'])],
            ('q1', '0', 'L'): [('q1', ['L', 'L'])],
            ('q1', '1', 'L'): [('q2', [])],
            ('q2', '1', 'L'): [('q2', [])],
            ('q2', '', '#'): [('q2', [])],  # Accept if only '#' is left
            ('q1', '', '#'): [('q2', [])]   # Accept on empty input
        }
    ),
    "Balanced Parentheses": PDA(
        states={'q1', 'q_accept'},
        input_alphabet={'(', ')'},
        stack_alphabet={'L', '#'},
        start_state='q1',
        start_stack='#',
        accept_states={'q_accept'},
        transitions={
            ('q1', '(', '#'): [('q1', ['L', '#'])],
            ('q1', '(', 'L'): [('q1', ['L', 'L'])],
            ('q1', ')', 'L'): [('q1', [])],
            ('q1', '', '#'): [('q_accept', [])]  # Accept only if stack is empty except for the initial stack symbol
        }
    )
}
app = PDAApp(root, pdas)
root.mainloop()