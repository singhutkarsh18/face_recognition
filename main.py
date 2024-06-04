import datetime
import os.path
import pickle
import tkinter as tk
import requests

import cv2
import face_recognition
from PIL import Image, ImageTk

import util
from test import test


class App:
    def __init__(self):
        self.main_window = tk.Tk()
        self.main_window.geometry("1200x520+350+100")

        def on_select(event):
            self.selected_party = event
            print("Selected option:", self.selected_party)

        left_margin = 750
        tk.Label(self.main_window, text="Select a party:").grid(row=0, column=0, padx=(left_margin, 10),
                                                                            pady=10, sticky="e")

        party_url = 'http://localhost:8080/parties'
        try:
            response = requests.get(party_url)
            # List of party names
            party_list = response.json()
        except requests.exceptions.RequestException as e:
            print(e)

        # Create a dropdown menu
        selected_option = tk.StringVar(self.main_window)
        selected_option.set(party_list[0])  # Set the default option
        dropdown = tk.OptionMenu(self.main_window, selected_option, *party_list, command=on_select)
        dropdown.grid(row=0, column=1, padx=10, pady=10)
        dropdown.config(width=30,height=3)

        self.login_button_main_window = util.get_button(self.main_window, 'Vote', 'green', self.login)
        self.login_button_main_window.place(x=750, y=300)


        self.register_new_user_button_main_window = util.get_button(self.main_window, 'Register new user', 'gray',
                                                                    self.register_new_user, fg='black')
        self.register_new_user_button_main_window.place(x=750, y=400)

        self.webcam_label = util.get_img_label(self.main_window)
        self.webcam_label.place(x=10, y=0, width=700, height=500)

        self.add_webcam(self.webcam_label)

        self.db_dir = './db'
        if not os.path.exists(self.db_dir):
            os.mkdir(self.db_dir)

        self.log_path = './log.txt'


    def add_webcam(self, label):
        if 'cap' not in self.__dict__:
            self.cap = cv2.VideoCapture(0)

        self._label = label
        self.process_webcam()

    def process_webcam(self):
        ret, frame = self.cap.read()

        self.most_recent_capture_arr = frame
        img_ = cv2.cvtColor(self.most_recent_capture_arr, cv2.COLOR_BGR2RGB)
        self.most_recent_capture_pil = Image.fromarray(img_)
        imgtk = ImageTk.PhotoImage(image=self.most_recent_capture_pil)
        self._label.imgtk = imgtk
        self._label.configure(image=imgtk)

        self._label.after(20, self.process_webcam)

    def login(self):
        vote_url = 'http://localhost:8080/votes'
        label = test(
            image=self.most_recent_capture_arr,
            model_dir="D:\\FaceRecog\\face_recog\\Silent_Face_Anti_Spoofing\\resources\\anti_spoof_models",
            device_id=0
        )
        if label == 1:

            name = util.recognize(self.most_recent_capture_arr, self.db_dir)

            if name in ['unknown_person', 'no_persons_found']:
                util.msg_box('Ups...', 'Unknown user. Please register new user or try again.')
            else:
                util.msg_box('Welcome back !', 'Welcome, {}.'.format(name))
                data = {
                    'partyName': self.selected_party,
                    'userName': name
                }
                requests.post(vote_url,json=data)
                with open(self.log_path, 'a') as f:
                    f.write('{},{},in\n'.format(name, datetime.datetime.now()))
                    f.close()

        else:
            util.msg_box('Hey, you are a spoofer!', 'You are fake !')


    def register_new_user(self):
        self.register_new_user_window = tk.Toplevel(self.main_window)
        self.register_new_user_window.geometry("1300x520+370+120")

        self.accept_button_register_new_user_window = util.get_button(self.register_new_user_window, 'Accept', 'green',
                                                                      self.accept_register_new_user)
        self.accept_button_register_new_user_window.place(x=750, y=300)

        self.try_again_button_register_new_user_window = util.get_button(self.register_new_user_window, 'Try again',
                                                                         'red', self.try_again_register_new_user)
        self.try_again_button_register_new_user_window.place(x=750, y=400)

        self.capture_label = util.get_img_label(self.register_new_user_window)
        self.capture_label.place(x=10, y=0, width=700, height=500)

        self.add_img_to_label(self.capture_label)

        aadhar = tk.StringVar(self.main_window)
        user_name = tk.StringVar(self.main_window)
        dob = tk.StringVar(self.main_window)
        father_name = tk.StringVar(self.main_window)
        city = tk.StringVar(self.main_window)
        left_margin = 700

        tk.Label(self.register_new_user_window, text="Aadhar Number:").grid(row=0, column=0, padx=(left_margin, 10),
                                                                            pady=5, sticky="e")
        entry_aadhar = tk.Entry(self.register_new_user_window, textvariable=aadhar, width=30)
        entry_aadhar.grid(row=0, column=1, padx=10, pady=5)

        tk.Label(self.register_new_user_window, text="Name:").grid(row=1, column=0, padx=(left_margin, 10), pady=5,
                                                                   sticky="e")
        entry_name = tk.Entry(self.register_new_user_window, textvariable=user_name, width=30)
        entry_name.grid(row=1, column=1, padx=10, pady=5)

        tk.Label(self.register_new_user_window, text="Date of Birth:").grid(row=2, column=0, padx=(left_margin, 10),
                                                                            pady=5, sticky="e")
        entry_dob = tk.Entry(self.register_new_user_window, textvariable=dob, width=30)
        entry_dob.grid(row=2, column=1, padx=10, pady=5)

        tk.Label(self.register_new_user_window, text="Father's Name:").grid(row=3, column=0, padx=(left_margin, 10),
                                                                            pady=5, sticky="e")
        entry_father_name = tk.Entry(self.register_new_user_window, textvariable=father_name, width=30)
        entry_father_name.grid(row=3, column=1, padx=10, pady=5)

        tk.Label(self.register_new_user_window, text="City:").grid(row=4, column=0, padx=(left_margin, 10), pady=5,
                                                                   sticky="e")
        entry_city = tk.Entry(self.register_new_user_window, textvariable=city, width=30)
        entry_city.grid(row=4, column=1, padx=10, pady=5)

        self.aadhar = aadhar

        self.user_name = user_name

        self.dob = dob

        self.city = city

        self.father_name = father_name

        print('aadhar', aadhar)
        print('user_name', user_name)

    def try_again_register_new_user(self):
        self.register_new_user_window.destroy()

    def add_img_to_label(self, label):
        imgtk = ImageTk.PhotoImage(image=self.most_recent_capture_pil)
        label.imgtk = imgtk
        label.configure(image=imgtk)

        self.register_new_user_capture = self.most_recent_capture_arr.copy()

    def start(self):
        self.main_window.mainloop()

    def accept_register_new_user(self):
        name = self.user_name.get() + self.aadhar.get()
        print("Name ====== ",name)
        embeddings = face_recognition.face_encodings(self.register_new_user_capture)[0]

        file = open(os.path.join(self.db_dir, '{}.pickle'.format(name)), 'wb')
        pickle.dump(embeddings, file)

        # Define the URL of the API endpoint
        url = 'http://localhost:8080/people'  # Adjust the URL as per your Spring Boot application's configuration

        # Define the data to be sent in the request body
        data = {
            'aadharNumber': self.aadhar.get(),
            'name': self.user_name.get(),
            'dateOfBirth': self.dob.get(),
            'fatherName': self.father_name.get(),
            'city': self.city.get()
        }

        # Send the POST request
        response = requests.post(url, json=data)

        # Check the response status code and content
        if response.status_code == 201:
            print("Person created successfully!")
            print("Response:", response.text)
        else:
            print("Failed to create person.")
            print("Response:", response.text)

        util.msg_box('Success!', 'User was registered successfully !')

        self.register_new_user_window.destroy()


if __name__ == "__main__":
    # sys.path.insert(0, './Silent_Face_Anti_Spoofing')
    app = App()
    app.start()
