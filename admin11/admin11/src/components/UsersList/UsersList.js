import React, { useState, useEffect } from "react";
import axios from "axios";
import UserTableRow from "./UserTableRow";
import EditUser from "./EditUser";
import { useNavigate } from "react-router-dom";

const UserList = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const navigate = useNavigate();

  const handleSearchChange = (event) => {
    setSearchQuery(event.target.value);
  };

  const token = localStorage.getItem('token');
  const config = {
    headers: {
        "Content-type": "application/json",
          "Authorization": token ? `Bearer ${token}` : null,
    },
  };

  useEffect(() => {
    axios.get("http://localhost:5001/users/getAll", config)
      .then((response) => {
        setUsers(response.data);
      })
      .catch((error) => {
        if (error.response.data.status == 500){
          navigate("/signin");
        }
        console.error("Error fetching user data: ", error);
      });
  }, [searchQuery]);

  const handleDeleteUser = (user) => {
    axios
      .post(`http://localhost:5001/users/delete?username=`+user.username, "",config)
      .then(() => {
        window.alert("user deleted successfully.");
        setSearchQuery(" ");  
      })
      .catch((error) => {
        if (error.response.data.status == 500){
          navigate("/signin");
        }
        console.error("Error deleting user: ", error);
      });
  };

  const handleEditClick = (user) => {
    setSelectedUser(user);
  };

  const handleCancelEdit = () => {
    setSelectedUser(null);
  };

  const handleUpdateUser = (updatedUser) => {
    setUsers(updatedUser);
  };

  return (
    <div className="container mx-auto p-6">
      <h1 className="text-3xl font-semibold mb-4">User List</h1>
      <div className="mb-4">
        <input
          type="text"
          value={searchQuery}
          onChange={handleSearchChange}
          className="border rounded-md px-3 py-2 w-full"
          placeholder="Search by username"
        />
      </div>
      <div className="overflow-x-auto">
        <table className="min-w-full table-auto">
          <thead>
            <tr className="bg-blue-500 text-white">
              <th className="px-4 py-2">Name</th>
              <th className="px-4 py-2">Username</th>
              <th className="px-4 py-2">Email</th>
              <th className="px-4 py-2">Age</th>
              <th className="px-4 py-2">Gender</th>
              <th className="px-4 py-2">Address</th>
              <th className="px-4 py-2">City</th>
              <th className="px-4 py-2">Country</th>
              <th className="px-4 py-2">Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <UserTableRow
                key={user._id}
                user={user}
                onDeleteClick={() => handleDeleteUser(user)}
                onUpdateClick={handleEditClick}
              />
            ))}
          </tbody>
        </table>
      </div>
      {selectedUser && (
        <EditUser
          user={selectedUser}
          onCancelEdit={handleCancelEdit}
          onUpdateUser={handleUpdateUser}
        />
      )}
    </div>
  );
};

export default UserList;
