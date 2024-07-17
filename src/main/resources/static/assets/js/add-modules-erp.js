const extractDate = dateString => {
    const match = dateString.match(/\d{2}-\d{2}-\d{4}/);
    if (match) {
        const [day, month, year] = match[0].split('-');
        return `${year}-${month}-${day}`;
    }
    else if (dateString.match(/\d{4}-\d{2}-\d{2}/)) {
        return dateString;
    }
    return null;
};
async function handleFormSubmission(event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);
    const jsonData = {};

    // Convert FormData to JSON
    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    // Add status and permission fields
    jsonData['status'] = true;
    jsonData['permission'] = [];
    jsonData['dob'] = extractDate(jsonData['dob']);
    jsonData['doj'] = extractDate(jsonData['doj']);
    // Validate required fields
    const requiredFields = [
        'firstName',
        'lastName',
        'personalEmail',
        'professionalEmail',
        'dob',
        'doj',
        'phone',
        'gender',
        'employeeSubLevelId',
        'company',
        'departmentId',
        'designationId',
        'roleId',
        'shiftId',
        'employeeStatusId',
        'currentStatusId',
        'rm',
        'secondRm'
    ];

    for (let field of requiredFields) {
        if (!jsonData[field] || jsonData[field].trim() === "") {
            Swal.fire({
                icon: 'error',
                title: 'Validation Error',
                text: `Please fill out the ${field.replace(/([A-Z])/g, ' $1').trim()} field.`
            });
            return;
        }
    }

    // Validate email fields
    const emailFields = ['personalEmail', 'professionalEmail'];
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    for (let emailField of emailFields) {
        if (jsonData[emailField] && !emailPattern.test(jsonData[emailField])) {
            Swal.fire({
                icon: 'error',
                title: 'Validation Error',
                text: `Please enter a valid email address for ${emailField.replace(/([A-Z])/g, ' $1').trim()}.`
            });
            return;
        }
    }
    // If validation passes, send the data
    try {
        const response = await fetch('/rest/add-employee', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonData)
        });

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Employee data submitted successfully!'
            });
            location.href = "/employees";
        } else {
            const errorData = await response.text();
            Swal.fire({
                icon: 'error',
                title: 'Submission Error',
                text: errorData || 'An error occurred while submitting the data.'
            });
        }
    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Network Error',
            text: 'An error occurred while submitting the data.'
        });
    }
}
async function myDesignationsbasedOnDepartment(departmentId) {
    await myGenricDesignationsbasedOnDepartment(departmentId, 'designationId')
}
async function myGenricDesignationsbasedOnDepartment(departmentId, insertDesignationId) {
    let url = `/rest/designations-by-department/${departmentId}`;

    try {
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        const data = await response.json();
        const designationSelect = document.getElementById(insertDesignationId);
        designationSelect.innerHTML = '<option value="" >Select Designation</option>'; // Reset options

        data.forEach(designation => {
            const option = document.createElement('option');
            option.value = designation.id;
            option.text = designation.designationName;
            designationSelect.add(option);
        });
    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Failed to load designations. Please try again later.'
        });
        console.error('Error fetching designations:', error);
    }
}


function submitDepartmentFunction(event) {
    event.preventDefault();
    const departmentName = document.getElementById('departmentName').value.trim();

    if (departmentName === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Department Name is required.'
        });
        return;
    }

    fetch('/rest/add-department', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            departmentName: departmentName
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Department added successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the new department in the dropdown
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to add department. Please try again later.'
            });
            console.error('Error adding department:', error);
        });
}

// document.getElementById('submitDesignationBtn').addEventListener('click', submitDesignationFunction);

function submitDesignationFunction(event) {
    event.preventDefault();
    const departmentId = document.getElementById('department-Id').value.trim();
    const designationName = document.getElementById('designation-Name').value.trim();

    if (departmentId === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Department  is required.'
        });
        return;
    }

    if (designationName === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Designation Name is required.'
        });
        return;
    }

    fetch('/rest/add-designation', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            departmentId: departmentId,
            designationName: designationName
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Designation added successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the new designation in the dropdown
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to add designation. Please try again later.'
            });
            console.error('Error adding designation:', error);
        });
}


function setSelectedValueAndMoveToTop(selectElementId, valueToSelect) {

    const selectElement = document.getElementById(selectElementId);
    const options = Array.from(selectElement.options);

    options.forEach(option => option.selected = false);


    const selectedOption = options.find(option => {
        return option.value == valueToSelect
    });

    if (selectedOption) {

        selectedOption.selected = true;
        const clonedSelectedOption = selectedOption.cloneNode(true);


        selectedOption.remove();


        selectElement.insertBefore(clonedSelectedOption, selectElement.firstChild);


        selectElement.value = valueToSelect;
    }

}



function openEdittDesignationModal(element) {
    const id = element.getAttribute('data-id');
    const depId = element.getAttribute('dep-id');
    const desName = element.getAttribute('des-name');
    setSelectedValueAndMoveToTop("editdepartmentId", depId);
    document.getElementById('editDesignationId').value = id;
    document.getElementById('editDesignationName').value = desName;




}
function submitEditDesignation(event) {
    const id = document.getElementById('editDesignationId').value;
    const name = document.getElementById('editDesignationName').value.trim();
    const dpartId = document.getElementById('editdepartmentId').value;

    if (name === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Designation Name is required.'
        });
        return;
    }
    event.preventDefault();

    fetch(`/rest/update-designation/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            designationName: name,
            departmentId: dpartId
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Designation updated successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the updated department
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to update department. Please try again later.'
            });
            console.error('Error updating department:', error);
        });
}

function confirmDesignationDelete(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this Designation?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteDesignation(id);
        }
    });
}

function deleteDesignation(id) {
    let idInt = parseInt(id);
    fetch(`/rest/delete-designation/${idInt}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Designation has been deleted.'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the updated department list
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to delete department. Please try again later.'
            });
            console.error('Error deleting department:', error);
        });
}

//  add department
function openEditDepartmentModal(element) {
    const id = element.getAttribute('data-id');
    const name = element.getAttribute('data-name');
    document.getElementById('editDepartmentId').value = id;
    document.getElementById('editDepartmentName').value = name;
}
function submitEditDepartment() {
    const id = document.getElementById('editDepartmentId').value;
    const name = document.getElementById('editDepartmentName').value.trim();

    if (name === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Department Name is required.'
        });
        return;
    }
    event.preventDefault();

    fetch(`/rest/update-department/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({

            departmentName: name
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Department updated successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the updated department
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to update department. Please try again later.'
            });
            console.error('Error updating department:', error);
        });
}

function confirmDepartmentDelete(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this department?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteDepartment(id);
        }
    });
}

function deleteDepartment(id) {
    let idInt = parseInt(id);
    fetch(`/rest/delete-department/${idInt}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Department has been deleted.'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the updated department list
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to delete department. Please try again later.'
            });
            console.error('Error deleting department:', error);
        });
}
// add module and sub module

function submitModuleFunction(event) {
    event.preventDefault();
    const moduleName = document.getElementById('moduleName').value.trim();

    if (moduleName === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Module Name is required.'
        });
        return;
    }

    fetch('rest/add-module', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            moduleName: moduleName
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Module added successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the new module in the table
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to add module. Please try again later.'
            });
            console.error('Error adding module:', error);
        });
}
function submitSubModuleFunction(event) {
    event.preventDefault();
    const moduleId = document.getElementById('moduleId').value.trim();
    const subModuleName = document.querySelector('input[name="name"]').value.trim();

    if (moduleId === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Module is required.'
        });
        return;
    }

    if (subModuleName === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Sub Module Name is required.'
        });
        return;
    }

    fetch('/rest/add-submodule', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            moduleId: moduleId,
            name: subModuleName
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Sub Module added successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the new sub-module in the table
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to add sub-module. Please try again later.'
            });
            console.error('Error adding sub-module:', error);
        });
}

function openEditModuleModal(element) {
    const id = element.getAttribute('data-id');
    const name = element.getAttribute('data-name');
    document.getElementById('editModuleId').value = id;
    document.getElementById('editModuleName').value = name;
}

function submitEditModule(event) {
    event.preventDefault();
    const id = document.getElementById('editModuleId').value;
    const name = document.getElementById('editModuleName').value.trim();

    if (name === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Module Name is required.'
        });
        return;
    }

    fetch(`rest/update-module/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            moduleName: name
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.json();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Module updated successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the updated module
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to update module. Please try again later.'
            });
            console.error('Error updating module:', error);
        });
}

function confirmModuleDelete(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this module?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteModule(id);
        }
    });
}

function deleteModule(id) {
    let mId = parseInt(id);
    fetch(`/rest/delete-module/${mId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Module has been deleted.'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the changes
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to delete module. Please try again later.'
            });
            console.error('Error deleting module:', error);
        });
}

function openEditSubModuleModal(element) {
    const id = element.getAttribute('data-id');
    const moduleId = element.getAttribute('data-module-id');
    const subModuleName = element.getAttribute('data-name');

    document.getElementById('editSubModuleId').value = id;
    document.getElementById('editModuleId').value = moduleId;
    document.getElementById('editSubModuleName').value = subModuleName;
}

function submitEditSubModule(event) {
    event.preventDefault();

    const id = document.getElementById('editSubModuleId').value;
    const moduleId = document.getElementById('editModuleId').value;
    const subModuleName = document.getElementById('editSubModuleName').value.trim();

    if (subModuleName === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Sub-Module Name is required.'
        });
        return;
    }

    fetch(`/rest/update-submodule/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            moduleId: moduleId,
            subModuleName: subModuleName
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Sub-Module updated successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the updated sub-module
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to update sub-module. Please try again later.'
            });
            console.error('Error updating sub-module:', error);
        });
}
function openEditSubModuleModal(element) {
    const id = element.getAttribute('data-id');
    const moduleId = element.getAttribute('data-module-id');
    const subModuleName = element.getAttribute('data-name');

    setSelectedValueAndMoveToTop("editSubModuleModuleId", moduleId);
    document.getElementById('editSubModuleId').value = id;
    document.getElementById('editSubModuleModuleId').value = moduleId;
    document.getElementById('editSubModuleName').value = subModuleName;

}

function submitEditSubModule(event) {
    event.preventDefault();

    const id = document.getElementById('editSubModuleId').value;
    const moduleId = document.getElementById('editSubModuleModuleId').value;
    const subModuleName = document.getElementById('editSubModuleName').value.trim();

    if (subModuleName === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Sub-Module Name is required.'
        });
        return;
    }

    fetch(`/rest/update-submodule/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            moduleId: moduleId,
            name: subModuleName
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Sub-Module updated successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the updated sub-module
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to update sub-module. Please try again later.'
            });
            console.error('Error updating sub-module:', error);
        });
}
function confirmSubModuleDelete(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this Sub-Module?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteSubModule(id);
        }
    });
}

function deleteSubModule(id) {
    let mId = parseInt(id);
    fetch(`/rest/delete-submodule/${mId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Sub-Module has been deleted.'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Reload the page to see the changes
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to delete Sb-Module. Please try again later.'
            });
            console.error('Error deleting Sub-Module:', error);
        });
}

function fetchSubModulesByModuleId(moduleId) {
    const subModuleTableBody = document.getElementById('tableDataForSubModule').querySelector('tbody');
    subModuleTableBody.innerHTML = ''; // Clear previous rows

    if (moduleId === '') {
        return; // If no module is selected, exit the function
    }

    fetch(`/rest/submodules-by-moduleId?id=${moduleId}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.json();
        })
        .then(subModules => {
            subModules.forEach((subModule, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${subModule.module.moduleName}</td>
                    <td>${subModule.name}</td>
                
                `;
                subModuleTableBody.appendChild(row);
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to load sub-modules. Please try again later.'
            });
            console.error('Error fetching sub-modules:', error);
        });
}
// Add role

function submitRoleFunction(event) {
    event.preventDefault();
    const roleName = document.getElementById('roleName').value.trim();

    if (roleName === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Role Name is required.'
        });
        return;
    }

    fetch('/rest/add-role', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            role: roleName
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Role added successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload();
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to add role. Please try again later.'
            });
            console.error('Error adding role:', error);
        });
}

function openEditRoleModal(element) {
    const id = element.getAttribute('data-id');
    const name = element.getAttribute('data-name');
    document.getElementById('editRoleId').value = id;
    document.getElementById('editRoleName').value = name;
}

function submitEditRole(event) {
    event.preventDefault();
    const id = document.getElementById('editRoleId').value;
    const name = document.getElementById('editRoleName').value.trim();

    if (name === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Role Name is required.'
        });
        return;
    }

    fetch(`/rest/update-role/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            role: name
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Role updated successfully!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload();
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to update role. Please try again later.'
            });
            console.error('Error updating role:', error);
        });
}

function confirmRoleDelete(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this role?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteRole(id);
        }
    });
}

function deleteRole(id) {
    fetch(`/rest/delete-role/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Role has been deleted.'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload();
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to delete role. Please try again later.'
            });
            console.error('Error deleting role:', error);
        });
}

// Fetch Moddule By role
function fetchModulesByRole(roleId, event) {
    $('ul.role-list li').removeClass('active');
    event.currentTarget.classList.add('active');

    // AJAX request to fetch modules based on roleId
    $.ajax({
        url: '/get-module-By-UserRole/' + roleId,
        type: 'GET',
        success: function (response) {

            $('#insertCode').html(response);
        },
        error: function () {
            console.error('Error fetching modules');
        }
    });
}

// role module permission

function toggleSubmoduleCheckboxes(checkbox, moduleId) {
    const submoduleCheckboxes = document.getElementById(moduleId);

    (submoduleCheckboxes.querySelectorAll("input")).forEach(cb => cb.checked = checkbox.checked);
}
function savePermission(moduleId, roleId) {
    // Collect all checked and unchecked checkboxes
    const checkedPermissions = [];
    const uncheckedPermissions = [];

    // Select all checkboxes within the specified module accordion
    const checkboxes = document.querySelectorAll(`#${moduleId} input[type=checkbox]`);

    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {

            checkedPermissions.push(parseInt(checkbox.value));
        } else {
            uncheckedPermissions.push(parseInt(checkbox.value));
        }
    });

    // Make AJAX calls to assign and unassign role permissions
    // Example AJAX calls using fetch API
    fetch('/rest/assign-and-unassign-role-permission', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            roleId: roleId,
            checkedPermissionIds: checkedPermissions,
            unCheckedPermissionIds: uncheckedPermissions
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data == true) {
                // Show success message or SweetAlert
                Swal.fire({
                    icon: 'success',
                    title: 'Permissions assigned successfully',
                    showConfirmButton: false,
                    timer: 1500
                });
            } else {
                // Handle error scenario
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.message
                });
            }
        })
        .catch(error => {
            console.error('Error assigning permissions:', error);
        });


}
// user permission js

function fetchEmployeeData(inputValue) {
    const userList = document.getElementById('user-list');

    if (inputValue.trim().length > 0) {
        fetch(`/rest/employees-like?id=${encodeURIComponent(inputValue)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json(); // Convert response to JSON
            })
            .then(data => {
                userList.innerHTML = ''; // Clear previous list items

                if (Array.isArray(data) && data.length > 0) {
                    data.forEach(item => {
                        const li = document.createElement('li');
                        const a = document.createElement('a');
                        a.href = 'javascript:void(0);';
                        a.textContent = item.firstName + " " + item.lastName + "  [" + item.id + "]"; // Assuming 'name' is the property to display
                        a.onclick = () => {
                            handleListItemClick(item.id); // Pass item ID to click handler
                        };
                        li.appendChild(a);
                        userList.appendChild(li);
                    });

                    const jsonData = JSON.stringify(data);
                } else {
                    // No employees found
                    Swal.fire({
                        icon: 'info',
                        title: 'No Employees Found',
                        text: 'There are no employees with this ID.',
                        confirmButtonText: 'OK'
                    });
                }
            })
            .catch(error => {
                console.error('Fetch error:', error);
                Swal.fire({
                    icon: 'error',
                    title: 'Error fetching employee data',
                    text: 'Please try again later',
                    confirmButtonText: 'OK'
                });
                $('#insertCode').html("");
            });
    } else {
        userList.innerHTML = '';
        $('#insertCode').html("");
        // Clear the list if input is empty
    }
}
function handleListItemClick(employeeId) {
    // Remove 'active' class from all list items
    const listItems = document.querySelectorAll('#user-list li');
    listItems.forEach(item => {
        item.classList.remove('active');
    });

    // Perform AJAX call using employeeId
    $.ajax({
        url: '/get-module-By-employee/' + employeeId,
        type: 'GET',
        success: function (response) {

            $('#insertCode').html(response);
        },
        error: function () {
            console.error('Error fetching modules');
        }
    });
}
function saveEmployeePermission(moduleId, userId) {
    // Collect all checked and unchecked checkboxes
    const checkedPermissions = [];
    const uncheckedPermissions = [];

    // Select all checkboxes within the specified module accordion
    const checkboxes = document.querySelectorAll(`#${moduleId} input[type=checkbox]`);

    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {

            checkedPermissions.push(parseInt(checkbox.value));
        } else {
            uncheckedPermissions.push(parseInt(checkbox.value));
        }
    });


    // Make AJAX calls to assign and unassign role permissions
    // Example AJAX calls using fetch API
    fetch('/assign-and-unassign-user-permission', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userId: userId,
            checkedPermissionIds: checkedPermissions,
            unCheckedPermissionIds: uncheckedPermissions
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data == true) {
                // Show success message or SweetAlert
                Swal.fire({
                    icon: 'success',
                    title: 'Permissions assigned successfully',
                    showConfirmButton: false,
                    timer: 1500
                });
            } else {
                // Handle error scenario
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.message
                });
            }
        })
        .catch(error => {
            console.error('Error assigning permissions:', error);
        });


}


/// fetching list data
async function filterEmployees() {
    // Retrieve input values from the form
    const userId = document.getElementById('userIdInput').value;
    const name = document.getElementById('nameInput').value;
    const departmentId = document.getElementById('departmentIdInput').value;

    // Construct the URL with query parameters
    const params = new URLSearchParams();
    if (userId) params.append('userId', userId);
    if (name) params.append('name', name);
    if (departmentId) params.append('departmentId', departmentId);
    const url = `rest/employees/filter?${params.toString()}`;

    try {
        // Fetch data from the backend
        const response = await fetch(url);
        const employees = await response.json();

        const employeeTableBodyActive = document.getElementById('employeeTableBodyActive');
        const employeeTableBodyInactive = document.getElementById('employeeTableBodyInactive');
        employeeTableBodyActive.innerHTML = ''; // Clear previous rows
        employeeTableBodyInactive.innerHTML = '';

        if (employees.length === 0) {
            // If no employees found, display a message in both tables
            const noEmployeesMessage = `
                <tr>
                    <td colspan="8" class="text-center text-muted">No employees found.</td>
                </tr>
            `;
            employeeTableBodyActive.innerHTML = noEmployeesMessage;
            employeeTableBodyInactive.innerHTML = noEmployeesMessage;
        } else {
            employees.forEach(employee => {
                const employeeRow = `
                    <td>${employee.id}</td>
                    <td>${employee.firstName} ${employee.lastName} <span>(${employee.designationId.designationName})</span></td>
                    <td>${employee.professionalEmail}</td>
                    <td>${employee.pphoneNumbr}</td>
                    <td>${employee.doj}</td>
                    <td>${employee.roleId.role}</td>
                    <td class="text-end">
                        <div class="dropdown dropdown-action">
                            <a href="#" class="action-icon dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="material-icons">more_vert</i>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right">
                                <a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#edit_employee" onclick="populateEditEmployeeForm(${employee.id})">
                                    <i class="fa-solid fa-pencil m-r-5"></i> Edit
                                </a>
                                <a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#delete_employee">
                                    <i class="fa-regular fa-trash-can m-r-5"></i> Delete
                                </a>
                            </div>
                        </div>
                    </td>
                `;

                if (employee.status) {
                    const newRow = employeeTableBodyActive.insertRow();
                    newRow.innerHTML = employeeRow;
                } else {
                    const newRow = employeeTableBodyInactive.insertRow();
                    newRow.innerHTML = employeeRow;
                }
            });
        }
    } catch (error) {
        console.error('Error fetching or parsing data:', error);
        // Handle error (e.g., display an error message)
        const errorMessage = `
            <tr>
                <td colspan="8" class="text-center text-danger">Error fetching employees. Please try again later.</td>
            </tr>
        `;
        document.getElementById('employeeTableBodyActive').innerHTML = errorMessage;
        document.getElementById('employeeTableBodyInactive').innerHTML = errorMessage;
    }
}


// employess

async function filterEmployeesForBox() {
    // Retrieve input values from the form
    const userId = document.getElementById('userIdInput').value;
    const name = document.getElementById('nameInput').value;
    const departmentId = document.getElementById('departmentIdInput').value;
    console.log(userId, name, departmentId);
    // Construct the URL with query parameters
    const params = new URLSearchParams();
    if (userId) params.append('userId', userId);
    if (name) params.append('name', name);
    if (departmentId) params.append('departmentId', departmentId);
    const url = `rest/employees/filter?${params.toString()}`;

    try {
        // Fetch data from the backend
        const response = await fetch(url);
        const employees = await response.json();

        // Handle the response (update boxes with filtered employees)
        const insertBoxDataActive = document.getElementById('insertBoxDataActive');
        const insertBoxDataInactive = document.getElementById('insertBoxDataInactive');
        insertBoxDataActive.innerHTML = ''; // Clear previous content
        insertBoxDataInactive.innerHTML = '';

        if (employees.length === 0) {
            // If no employees found, display a message in both sections
            const noEmployeesMessage = `
                <div class="text-center text-muted">No employees found.</div>
            `;
            insertBoxDataActive.innerHTML = noEmployeesMessage;
            insertBoxDataInactive.innerHTML = noEmployeesMessage;
        } else {
            employees.forEach(emp => {

                const profileLink = `/profile/${emp.encodeUrl}`;
                const designation = emp.designationId ? emp.designationId.designationName : '';

                const employeeCard = `
                    <div class="col-md-4 col-sm-6 col-12 col-lg-4 col-xl-3">
                        <div class="profile-widget">
                            <div class="profile-img">
                                <a href="${profileLink}" class="avatar">
                                    <img src="assets/img/profiles/avatar-02.jpg" alt="User Image">
                                </a>
                            </div>
                            <div class="dropdown profile-action">
                                <a href="#" class="action-icon dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="material-icons">more_vert</i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right">
                                      <a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#edit_employee" onclick="populateEditEmployeeForm(${emp.id})">
                                    <i class="fa-solid fa-pencil m-r-5"></i> Edit
                                </a>
                                    <a class="dropdown-item delete-employee" href="#" data-employee-id="${emp.id}">
                                        <i class="fa-regular fa-trash-can m-r-5"></i> Delete
                                    </a>
                                </div>

                            </div>
                            <h4 class="user-name m-t-10 mb-0 text-ellipsis">
                                <a href="${profileLink}">
                                    ${emp.firstName} ${emp.lastName}
                                </a>
                            </h4>
                            <div class="small text-muted">${designation}</div>
                        </div>
                    </div>
                `;

                if (emp.status) {
                    insertBoxDataActive.insertAdjacentHTML('beforeend', employeeCard);
                } else {
                    insertBoxDataInactive.insertAdjacentHTML('beforeend', employeeCard);
                }
            });

            // Add event listeners for the Edit and Delete buttons
            // document.querySelectorAll('.edit-employee').forEach(button => {
            //     button.addEventListener('click', event => {
            //         event.preventDefault();
            //         const employeeId = button.getAttribute('data-employee-id');
            //         // Call your edit function here
            //         populateEditEmployeeForm(employeeId);
            //     });
            // });

            // document.querySelectorAll('.delete-employee').forEach(button => {
            //     button.addEventListener('click', event => {
            //         event.preventDefault();
            //         const employeeId = button.getAttribute('data-employee-id');
            //         // Call your delete function here
            //         // showDeleteConfirmationModal(employeeId);
            //         console.log(`Delete employee with ID: ${employeeId}`);
            //     });
            // });
        }
    } catch (error) {
        console.error('Error fetching or parsing data:', error);
        // Handle error (e.g., display an error message)
        const errorMessage = `
            <div class="text-center text-danger">Error fetching employees. Please try again later.</div>
        `;
        document.getElementById('insertBoxDataActive').innerHTML = errorMessage;
        document.getElementById('insertBoxDataInactive').innerHTML = errorMessage;
    }
}


// Shift ccomplete methods

function formatTime(date) {
    return date.getHours().toString().padStart(2, '0') + ':' +
        date.getMinutes().toString().padStart(2, '0') + ':' +
        date.getSeconds().toString().padStart(2, '0');
}

function calculateSessions() {

    genricCalculateSessions('loginTime', 'logoffTime', 'numSessions', 'extraInHours', 'totalShiftTime', 'sessionTable')

}
function genricCalculateSessions(login, logout, numberOfSession, extra, displayTotalTime, displayId) {
    var startTime = document.getElementById(login).value + ":00";
    var endTime = document.getElementById(logout).value + ":00";
    var numSessions = parseInt(document.getElementById(numberOfSession).value);
    var extraInHours = parseInt(document.getElementById(extra).value);

    var inTime = parseTime(startTime);
    var outTime = parseTime(endTime);
    var totalShiftTime = formatDuration(startTime, endTime);
    var totalMinutes = timeToTotalMinutes(totalShiftTime);
    var sessionDuration = totalMinutes / numSessions;
    var extraTimeInMinutes = (extraInHours * 60) / numSessions;

    document.getElementById(displayTotalTime).value = totalShiftTime;

    var sessions = [];
    var j = 0;

    if (numSessions == 1) {
        var sessionInTime = inTime;
        var sessionOutTime = outTime;

        sessions.push({
            name: "Session 1",
            inTime: formatTime(sessionInTime),
            outTime: formatTime(sessionOutTime)
        });
    } else {
        for (var i = 0; i < numSessions; i++) {
            if (i < numSessions / 2) j++;
            else j--;

            var sessionInTime = new Date(inTime.getTime() + i * sessionDuration * 60000);
            var sessionOutTime = new Date(sessionInTime.getTime() + sessionDuration * 60000);
            sessionInTime.setMinutes(sessionInTime.getMinutes() - extraTimeInMinutes * i);
            sessionOutTime.setMinutes(sessionOutTime.getMinutes() + j * extraTimeInMinutes);

            sessions.push({
                name: 'Session ' + (i + 1),
                inTime: formatTime(sessionInTime),
                outTime: formatTime(sessionOutTime)
            });

        }
        sessions[sessions.length - 1].outTime = formatTime(parseTime(document.getElementById(logout).value));
    }

    displaySessions(sessions, displayId);

}
function timeToTotalMinutes(timeString) {
    var parts = timeString.split(':').map(Number);
    var totalMinutes = parts[0] * 60 + parts[1] + parts[2] / 60;
    return totalMinutes;
}


function parseTime(timeString) {
    var parts = timeString.split(':');
    if (parts.length < 3) {
        parts[2] = "00";
    }
    var date = new Date();
    date.setHours(parseInt(parts[0]), parseInt(parts[1]), parseInt(parts[2]));
    return date;
}



function displaySessions(sessions, id) {
    var table = document.getElementById(id).getElementsByTagName('tbody')[0];
    table.innerHTML = ''; // Clear existing rows

    sessions.forEach(function (session) {
        var newRow = table.insertRow();
        var cell1 = newRow.insertCell(0);
        var cell2 = newRow.insertCell(1);
        var cell3 = newRow.insertCell(2);

        cell1.innerHTML = session.name;
        cell2.innerHTML = `<input type="text" readonly value="${session.inTime}" pattern="^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$" placeholder="00:00:00">`;
        cell3.innerHTML = `<input type="text" readonly value="${session.outTime}" pattern="^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$" placeholder="00:00:00">`;
    });
}


function formatDuration(startTime, endTime) {
    // Parse start and end times
    var startParts = startTime.split(':').map(Number);
    var endParts = endTime.split(':').map(Number);

    // Convert times to total seconds since midnight
    var startSeconds = startParts[0] * 3600 + startParts[1] * 60 + startParts[2];
    var endSeconds = endParts[0] * 3600 + endParts[1] * 60 + endParts[2];

    // If end time is before start time, adjust for next day
    if (endSeconds < startSeconds) {
        endSeconds += 24 * 3600; // Add 24 hours (in seconds) for night shift
    }

    // Calculate total seconds difference
    var totalSeconds = endSeconds - startSeconds;

    // Calculate hours, minutes, seconds
    var hours = Math.floor(totalSeconds / 3600);
    totalSeconds %= 3600;
    var minutes = Math.floor(totalSeconds / 60);
    var seconds = totalSeconds % 60;

    // Format and return the duration
    return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
}


async function submitAddShift(event) {
    event.preventDefault(); // Prevent form submission

    // Validate form fields (example validation)
    var shiftName = document.getElementById('shiftName').value;
    var shiftCode = document.getElementById('shiftCode').value;
    var loginTime = document.getElementById('loginTime').value + ":00";
    var logoffTime = document.getElementById('logoffTime').value + ":00";
    var extraInHours = document.getElementById('extraInHours').value;
    var numSessions = document.getElementById('numSessions').value;
    var breakInMinute = document.getElementById('breakTime').value;
    var satOff = document.querySelector('input[name="saturdayOff"]:checked');
    var sunOff = document.querySelector('input[name="sundayOff"]:checked')
    // Example validation: Check if any required fields are empty
    if (!shiftName || !shiftCode || !loginTime || !logoffTime || !extraInHours || !numSessions || !breakInMinute || !satOff || !sunOff) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Please fill in all required fields!',
        });
        return;
    }

    // Example: Collect form data to submit (adjust as per your form structure)
    var formData = {
        shiftName: shiftName,
        shiftCode: shiftCode,
        loginTime: loginTime,
        logoutTime: logoffTime,
        extraTimeToWorkInHours: extraInHours,
        noOfSessions: numSessions,
        satOff: satOff.value,
        sunOff: sunOff.value,
        breakInMinute: breakInMinute
        // Add more fields as needed
    };

    // Example: Send formData to backend (replace with your API endpoint)
    fetch('/rest/add-shift', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text);
                });
            }
            return response.text();
        })
        .then(data => {
            // Example: Handle successful submission
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: 'Shift added successfully.',
            }).then((result) => {
                // Reload or redirect after successful submission
                location.reload(); // Example: Reload the page
            });
        })
        .catch(error => {
            console.error('Error:', error);
            // Show error message
            Swal.fire({
                icon: 'error',
                title: 'Submission Failed',
                text: error.message || 'Failed to add shift. Please try again later.',
            });
        });
}
function openEditShiftModal(data) {

    var shiftId = data.getAttribute("data-id");
    var shiftName = data.getAttribute("data-name");
    var shiftShortName = data.getAttribute("data-shortName");
    var login = data.getAttribute("data-login");
    var logout = data.getAttribute("data-logout");
    var breakInMinute = data.getAttribute("data-break");
    var extra = data.getAttribute("extra-work");
    var numberOfSession = data.getAttribute("no-session");
    var sunOff = data.getAttribute("sun-off");
    var satOff = data.getAttribute("sat-off");

    document.getElementById('editShiftId').value = shiftId
    document.getElementById('editShiftName').value = shiftName;
    document.getElementById('editShiftCode').value = shiftShortName;
    document.getElementById('editLoginTime').value = login;
    document.getElementById('editLogoutTime').value = logout
    document.getElementById('editBreakTime').value = breakInMinute;
    document.getElementById('editExtraTime').value = extra;
    document.getElementById("editNumberOfSessions").value = numberOfSession;
    document.querySelector('input[name="editSaturdayOff"][value="permanentOn"]').checked = satOff === "permanentOn";
    document.querySelector('input[name="editSaturdayOff"][value="permanentOff"]').checked = satOff === "permanentOff";
    document.querySelector('input[name="editSaturdayOff"][value="alternateOff"]').checked = satOff === "alternateOff";

    // Set radio buttons for Sunday Off
    document.querySelector('input[name="editSundayOff"][value="true"]').checked = sunOff === "true";
    document.querySelector('input[name="editSundayOff"][value="false"]').checked = sunOff === "false";

    editCalculateSessions();
    $("#edit_shift").modal('show');

}
function editCalculateSessions() {

    genricCalculateSessions('editLoginTime', 'editLogoutTime', 'editNumberOfSessions', 'editExtraTime', 'editTotalShiftTime', 'editTable')

}
function submitEditShift(event) {
    event.preventDefault(); // Prevent default form submission

    // Retrieve values from form inputs
    const shiftId = document.getElementById('editShiftId').value;
    const shiftName = document.getElementById('editShiftName').value;
    const shiftCode = document.getElementById('editShiftCode').value;
    const loginTime = document.getElementById('editLoginTime').value;
    const logoutTime = document.getElementById('editLogoutTime').value;
    const extraTime = document.getElementById('editExtraTime').value;
    const numberOfSessions = document.getElementById('editNumberOfSessions').value;
    const breakInMinute = document.getElementById('editBreakTime').value;

    // Retrieve selected radio button values
    const satOff = document.querySelector('input[name="editSaturdayOff"]:checked').value;
    const sunOff = document.querySelector('input[name="editSundayOff"]:checked').value;

    // Example log to verify data retrieval

    if (!shiftName || !shiftCode || !loginTime || !logoutTime || !extraInHours || !numSessions || !breakInMinute || !satOff || !sunOff) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Please fill in all required fields!',
        });
        return;
    }


    const endpoint = `/rest/update-shift/${shiftId}`;
    const formData = {
        shiftId: shiftId,
        shiftName: shiftName,
        shiftCode: shiftCode,
        loginTime: loginTime,
        logoutTime: logoutTime,
        breakInMinute: breakInMinute,
        satOff: satOff,
        sunOff: sunOff,
        noOfSessions: numberOfSessions,
        extraTime: extraTime

    };

    // Example fetch call to send data
    fetch(endpoint, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text);
                });
            }
            return response.text();
        })
        .then(data => {
            // Example: Handle successful submission
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: 'Shift Updated successfully.',
            }).then((result) => {
                location.reload(); // Example: Reload the page
            });
        })
        .catch(error => {
            console.error('Error:', error);
            // Show error message
            Swal.fire({
                icon: 'error',
                title: 'Submission Failed',
                text: error.message || 'Failed to add shift. Please try again later.',
            });
        });
}
function confirmShiftDelete(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this Shift?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteShift(id);
        }
    });
}

function deleteShift(id) {
    fetch(`/rest/delete-shift/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Shift has been deleted.'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload();
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to delete Shift. Please try again later.'
            });
            console.error('Error deleting role:', error);
        });
}

// Employee status 
function addEmployeeStatusForm(event) {
    event.preventDefault(); // Prevent default form submission

    // Collect form data
    var probationTimeInDays = document.getElementById('probationTimeInDays').value;
    var employeeStatus = document.getElementById('employeeStatus').value;
    var description = document.getElementById('description').value;

    // Prepare data object
    var formData = {
        probationTimeInDays: probationTimeInDays,
        employeeStatus: employeeStatus,
        description: description
    };

    // Make POST request using Fetch API
    fetch('/rest/add-employee-status', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(data => { throw new Error(data) });
            }
            return response.text();
        })
        .then(data => {
            // Show success message using SweetAlert
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Employee status added successfully!',
                confirmButtonText: 'OK'
            }).then(() => {
                // Optionally, you can reload or update the UI
                location.reload(); // Example: Reload the page
            });
        })
        .catch(error => {
            console.error('Error:', error);
            // Show error message using SweetAlert
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error || 'Failed to add employee status. Please try again.',
                confirmButtonText: 'OK'
            });
        });
}
function openEditEmployeeStatusModel(data) {
    var id = data.getAttribute("data-id");
    var probationTime = data.getAttribute("data-probation");
    var status = data.getAttribute("data-status");
    var description = data.getAttribute("data-des");
    setSelectedValueAndMoveToTop("editEmployeeStatus", status);
    // Populate modal form fields
    $('#editStatusId').val(id);
    $('#editProbationTime').val(probationTime);
    $('#editDescription').val(description);
    $('#edit_employee_status').modal('show');


}
function submitStatusFrom(event) {
    event.preventDefault();

    // Fetch form values
    var id = document.getElementById("editStatusId").value;
    var probationTime = document.getElementById("editProbationTime").value;
    var employeeStatus = document.getElementById("editEmployeeStatus").value;
    var description = document.getElementById("editDescription").value;

    // Client-side validation
    if (!probationTime || !employeeStatus || !description) {
        swal({
            title: "Validation Error",
            text: "Please fill in all required fields.",
            icon: "error",
        });
        return;
    }

    // Prepare data for POST request
    var formData = {
        probationTimeInDays: probationTime,
        employeeStatus: employeeStatus,
        description: description
    };

    // POST request using Fetch API
    fetch(`/rest/update-employee-status/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then((data) => { throw new Error(data); });
            }
            return response.json();
        })
        .then(data => {
            // Success handling
            Swal.fire({
                title: "Success",
                text: "Employee status updated successfully!",
                icon: "success",
                confirmButtonText: 'OK'
            }).then(() => {
                location.reload(); // Reload the page after success
            });
        })
        .catch(error => {
            // Error handling
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to update employee status. Please try again.',
                confirmButtonText: 'OK'
            });
        });
}
function confirmEmployeeStatusDelete(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this Status?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteEmployeeStatus(id);
        }
    });
}

function deleteEmployeeStatus(id) {
    fetch(`/rest/delete-employee-status/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Employee Status has been deleted.'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload();
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to delete Employee Status. Please try again later.'
            });
            console.error('Error deleting role:', error);
        });
}

// level and sub level
function submitAddLevel(event) {
    event.preventDefault(); // Prevent default form submission

    // Get form data
    const level = document.getElementById('levelName').value;
    const levelDescription = document.getElementById('levelDescription').value;

    // Validate form data
    if (!level || !levelDescription) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'All fields are required!',
            confirmButtonText: 'OK'
        });
        return;
    }

    // Create request payload
    const formData = {
        level,
        levelDescription
    };

    // Send POST request to add employee level
    fetch('/rest/add-employee-level', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then((data) => { throw new Error(data) });
            }
            return response.text();
        })
        .then(data => {
            // Success handling
            Swal.fire({
                title: 'Success',
                text: 'Employee level added successfully!',
                icon: 'success',
                confirmButtonText: 'OK'
            }).then(() => {
                location.reload(); // Reload the page to see the new data
            });
        })
        .catch(error => {
            // Error handling
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to add employee level. Please try again.',
                confirmButtonText: 'OK'
            });
        });
}
function submitAddSubLevel(event) {

    event.preventDefault();

    const employeeLevelId = document.getElementById('employeeLevelId').value;
    const subLevel = document.getElementById('subLevel').value;
    const subLevelTitle = document.getElementById('subLevelTitle').value;
    const subLevelQualification = document.getElementById('subLevelQualification').value;
    var subLevelDescription = document.getElementById('subLevelDescription').value;

    // Validate form fields
    if (!employeeLevelId || employeeLevelId === 'Select Level' || !subLevel || !subLevelTitle || !subLevelQualification || !subLevelDescription) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'All fields are required!',
            confirmButtonText: 'OK'
        });
        return;
    }

    const formData = {
        employeeLevelId: employeeLevelId,
        subLevel: subLevel,
        subLevelTitle: subLevelTitle,
        subLevelQualification: subLevelQualification,
        subLevelDescription: subLevelDescription

    };

    fetch('/rest/add-employee-sub-level', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then((data) => { throw new Error(data); });
            }
            return response.text();
        })
        .then(data => {
            // Success handling
            Swal.fire({
                title: 'Success',
                text: 'Employee Sublevel added successfully!',
                icon: 'success',
                confirmButtonText: 'OK'
            }).then(() => {
                location.reload(); // Reload the page to see the new data
            });


        })
        .catch(error => {
            // Error handling
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error || 'Failed to add Sub Level. Please try again.',
                confirmButtonText: 'OK'
            });
        }
        )
}

// Function to open edit modal and populate data
function openEditModal(levelId, levelName, levelDescription) {
    document.getElementById("editLevelId").value = levelId;
    document.getElementById('editLevelName').value = levelName;
    document.getElementById('editLevelDescription').value = levelDescription;


    // Show the modal
    $('#edit_level').modal('show');
}

// Function to handle form submission
function submitEditLevel() {
    // Fetch values from form

    var levelName = document.getElementById('editLevelName').value;
    var levelDescription = document.getElementById('editLevelDescription').value;
    var levelId = parseInt(document.getElementById("editLevelId").value);
    if (!levelName || !levelDescription) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'All fields are required!',
            confirmButtonText: 'OK'
        });
        return;
    }
    fetch(`/rest/update-employee-level/${levelId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            level: levelName,
            levelDescription: levelDescription
        })
    })
        .then(response => {
            if (!response.ok) {
                // If response is not okay, throw an error with response text
                return response.text().then(data => {
                    throw new Error(data);
                });
            }
            // If response is okay, return response text
            return response.text();
        })
        .then(data => {
            // Success handling
            Swal.fire({
                title: 'Success',
                text: ' Level updated successfully!',
                icon: 'success'
            }).then(data => {
                location.reload();
            });



        })
        .catch(error => {
            // Error handling
            console.error('Error:', error);
            // Show error message to user
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to update level. Please try again.',
                confirmButtonText: 'OK'
            });
        });
}
function openEditSubLevelModal(levelId, subLevelId, subLevelName, subLevelTitle, subLevelDescription, subLevelQualification) {
    document.getElementById('editSubLevelSelect').value = levelId;
    document.getElementById('editSubLevelName').value = subLevelName;
    document.getElementById('editSubLevelTitle').value = subLevelTitle;
    document.getElementById('editSubLevelDescription').value = subLevelDescription;
    document.getElementById('editSubLevelQualification').value = subLevelQualification;

    document.getElementById('editSubLevelId').value = subLevelId;

    // Show modal
    $('#edit_sublevel').modal('show');
}

function submitEditSubLevel() {
    // Fetch values from form
    var levelId = document.getElementById('editSubLevelSelect').value;
    var subLevelName = document.getElementById('editSubLevelName').value;
    var subLevelTitle = document.getElementById('editSubLevelTitle').value;
    var subLevelDescription = document.getElementById('editSubLevelDescription').value;
    var subLevelQualification = document.getElementById('editSubLevelQualification').value;
    var subLevelId = document.getElementById('editSubLevelId').value;
    setSelectedValueAndMoveToTop("editSubLevelSelect", levelId);
    if (!employeeLevelId || employeeLevelId === 'Select Level' || !subLevelName || !subLevelTitle || !subLevelQualification || !subLevelDescription) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'All fields are required!',
            confirmButtonText: 'OK'
        });
        return;
    }

    // Fetch this value as needed

    fetch(`/rest/update-employee-sub-level/${subLevelId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            employeeLevelId: levelId,
            subLevel: subLevelName,
            subLevelTitle: subLevelTitle,
            subLevelDescription: subLevelDescription,
            subLevelQualification: subLevelQualification

        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(data => {
                    throw new Error(data);
                });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                title: 'Success',
                text: 'Employee Sublevel updated successfully!',
                icon: 'success',
                confirmButtonText: 'OK'
            }).then(() => {
                location.reload(); // Reload the page to see the new data
            });

        })
        .catch(error => {
            // Error handling
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to update sub level. Please try again.',
                confirmButtonText: 'OK'
            });
        });
}
function confirmLevelDelete(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this Level?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteLevel(id);
        }
    });
}

function deleteLevel(id) {
    fetch(`/rest/delete-employee-level/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Employee Level has been deleted.'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload();
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to delete Employee Level. Please try again later.'
            });
            console.error('Error deleting level:', error);
        });
}
function confirmSubLevelDelete(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this Sub Level?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteSubLevel(id);
        }
    });
}

function deleteSubLevel(id) {
    fetch(`/rest/delete-employee-sub-level/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Employee Sub Level has been deleted.'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload();
                }
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: error.message || 'Failed to delete Employee Sub Level. Please try again later.'
            });
            console.error('Error deleting sub level:', error);
        });
}
// Edit Employee
async function myEditDesignationsbasedOnDepartment(departmentId, valueToSelect) {
    let url = `/rest/designations-by-department/${departmentId}`;

    try {
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        const data = await response.json();
        const designationSelect = document.getElementById('designationId');
        designationSelect.innerHTML = "";
        data.forEach(designation => {
            const option = document.createElement('option');
            option.value = designation.id;
            option.text = designation.designationName;
            if (designation == valueToSelect)
                option.selected = true;
            designationSelect.add(option);

        });

    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Failed to load designations. Please try again later.'
        });
        console.error('Error fetching designations:', error);
    }


}
async function handleEmployeeEditFormSubmission(event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);

    const jsonData = {};

    // Convert FormData to JSON
    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    // Add permission fields
    jsonData['permission'] = [];
    jsonData['dob'] = extractDate(document.getElementById('dob').value);
    jsonData['doj'] = extractDate(document.getElementById('doj').value);


    // Validate required fields
    const requiredFields = [
        'firstName',
        'lastName',
        'personalEmail',
        'professionalEmail',
        'dob',
        'doj',
        'phone',
        'gender',
        'employeeSubLevelId',
        'company',
        'departmentId',
        'designationId',
        'roleId',
        'shiftId',
        'employeeStatusId',
        'currentStatusId',
        'rm',
        'secondRm',
        'status'
    ];

    for (let field of requiredFields) {
        if (!jsonData[field] || jsonData[field].trim() === "") {
            Swal.fire({
                icon: 'error',
                title: 'Validation Error',
                text: `Please fill out the ${field.replace(/([A-Z])/g, ' $1').trim()} field.`
            });
            return;
        }
    }

    // Validate email fields
    const emailFields = ['personalEmail', 'professionalEmail'];
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    for (let emailField of emailFields) {
        if (jsonData[emailField] && !emailPattern.test(jsonData[emailField])) {
            Swal.fire({
                icon: 'error',
                title: 'Validation Error',
                text: `Please enter a valid email address for ${emailField.replace(/([A-Z])/g, ' $1').trim()}.`
            });
            return;
        }
    }


    // If validation passes, send the data
    var editId = document.getElementById('editId').value;
    try {
        const response = await fetch('/rest/update-employee/' + editId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonData)
        });

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Employee data submitted successfully!'
            });
            location.reload();
        } else {
            const errorData = await response.text();
            Swal.fire({
                icon: 'error',
                title: 'Submission Error',
                text: errorData || 'An error occurred while submitting the data.'
            });
        }
    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Network Error',
            text: 'An error occurred while submitting the data.'
        });
    }
}

async function populateEditEmployeeForm(employeeId) {
    console.log("employeeId ", employeeId);
    try {
        const response = await fetch(`/rest/employees/${employeeId}`); // Replace with your API endpoint
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text) });
        }
        const employeeData = await response.json();
        console.log(employeeData);
        document.getElementById('editId').value = employeeData.id;
        document.getElementById('firstName').value = employeeData.firstName;
        document.getElementById('lastName').value = employeeData.lastName;
        document.getElementById('personalEmail').value = employeeData.personalEmail;
        document.getElementById('professionalEmail').value = employeeData.professionalEmail;
        document.getElementById('dob').value = employeeData.dob;
        document.getElementById('doj').value = employeeData.doj;

        document.getElementById('phone').value = employeeData.pphoneNumbr;
        setSelectedValueAndMoveToTop('gender', employeeData.gender);
        setSelectedValueAndMoveToTop('employeeSubLevelId', employeeData.employeeSubLevelId.id);
        setSelectedValueAndMoveToTop('company', employeeData.company);
        setSelectedValueAndMoveToTop('departmentId', employeeData.departmentId.id);
        myEditDesignationsbasedOnDepartment(employeeData.departmentId.id, employeeData.designationId.id);
        setSelectedValueAndMoveToTop('roleId', employeeData.roleId.id);
        setSelectedValueAndMoveToTop('shiftId', employeeData.shiftId.id);
        setSelectedValueAndMoveToTop('employeeStatusId', employeeData.employeeStatusId.id);
        setSelectedValueAndMoveToTop('currentStatusId', employeeData.currentStatusId.id);
        setSelectedValueAndMoveToTop('rm', employeeData.rm);
        setSelectedValueAndMoveToTop('secondRm', employeeData.secondRm);
        setSelectedValueAndMoveToTop('status', employeeData.status + '');


    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: error.message || 'Failed to delete Employee Sub Level. Please try again later.'
        });
        console.error('Error deleting sub level:', error);
    }
}













