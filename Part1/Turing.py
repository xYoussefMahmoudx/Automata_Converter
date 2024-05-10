import tkinter as tk
from tkinter import messagebox, ttk
from tkinter.font import Font

# TuringMachine class
class TuringMachine:
    def __init__(self, states, input_symbols, tape_symbols, transitions, start_state, accept_state, reject_state, blank_symbol):
        self.states = states
        self.input_symbols = input_symbols
        self.tape_symbols = tape_symbols
        self.transitions = transitions
        self.current_state = start_state
        self.start_state = start_state
        self.accept_state = accept_state
        self.reject_state = reject_state
        self.blank_symbol = blank_symbol
        self.tape = [blank_symbol]
        self.head_position = 0

    def reset(self):
        self.current_state = self.start_state
        self.tape = [self.blank_symbol]
        self.head_position = 0

    def step(self):
        current_symbol = self.tape[self.head_position]
        key = (self.current_state, current_symbol)
        if key in self.transitions:
            new_state, write_symbol, move_dir = self.transitions[key]
            self.tape[self.head_position] = write_symbol
            self.current_state = new_state
            if move_dir == 'R':
                self.head_position += 1
                if self.head_position == len(self.tape):
                    self.tape.append(self.blank_symbol)
            elif move_dir == 'L':
                if self.head_position > 0:
                    self.head_position -= 1
                else:
                    self.tape.insert(0, self.blank_symbol)
        else:
            self.current_state = self.reject_state

    def process_input(self, input_string):
        #if not all(symbol in self.input_symbols for symbol in input_string):
            #raise ValueError("Invalid input string")
        self.reset()
        self.tape = list(input_string) + [self.blank_symbol]
        while self.current_state not in [self.accept_state, self.reject_state]:
            self.step()

    def is_accepted(self):
        return self.current_state == self.accept_state

    def get_tape_content(self):
        return ''.join(self.tape[:-1])

# TuringMachineApp class
class TuringMachineApp:
    def __init__(self, master, tms):
        self.master = master
        master.title("Turing Machine Simulator")
        master.geometry("400x300")

        self.font = Font(family="Helvetica", size=12)
        self.tms = tms
        self.tm = None

        self.label = tk.Label(master, text="Enter a string:", font=self.font)
        self.label.pack(pady=10)

        self.entry = tk.Entry(master, font=self.font)
        self.entry.pack(pady=10)

        self.tm_selector = ttk.Combobox(master, values=list(self.tms.keys()), state="readonly", font=self.font)
        self.tm_selector.pack(pady=10)
        self.tm_selector.bind("<<ComboboxSelected>>", self.select_tm)

        self.process_button = tk.Button(master, text="Process", command=self.process, font=self.font)
        self.process_button.pack(pady=10)

        self.result_label = tk.Label(master, text="", font=self.font)
        self.result_label.pack(pady=10)

    def select_tm(self, event):
        tm_name = self.tm_selector.get()
        self.tm = self.tms[tm_name]

    def process(self):
        input_string = self.entry.get()
        if self.tm:
            self.tm.process_input(input_string)
            result = "Accepted" if self.tm.is_accepted() else "Rejected"
            if result == "Accepted":
                output_tape = self.tm.get_tape_content()  # Get the current tape content if accepted
            else:
                output_tape = ""  # Set output to blank if rejected

            self.result_label.config(text=f"Result: {result}, Output: {output_tape}")
        else:
            messagebox.showinfo("Info", "Please select a Turing Machine first.")

# Main application setup with Turing Machines
root = tk.Tk()
tms = {
    "Binary Incrementer": TuringMachine(
        states={'q0', 'q1', 'q_accept', 'q_reject'},
        input_symbols={'0', '1'},
        tape_symbols={'0', '1', 'X'},
        transitions={
            ('q0', '0'): ('q0', '0', 'R'),
            ('q0', '1'): ('q0', '1', 'R'),
            ('q0', 'X'): ('q1', 'X', 'L'),
            ('q1', '0'): ('q_accept', '1', 'N'),
            ('q1', '1'): ('q1', '0', 'L'),
            ('q1', 'X'): ('q_accept', '1', 'N')
        },
        start_state='q0',
        accept_state='q_accept',
        reject_state='q_reject',
        blank_symbol='X'
    )
}
app = TuringMachineApp(root, tms)
root.mainloop()