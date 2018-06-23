<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Add Question</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
	</head>
		<body>

			<div class="container">
				<form id="addQuestionForm" action="add-question" method="post">
					<div class="row">
						<div class="col-md-12 mb-3 text-center">
							<label for="questionName">Enter Question</label> <input name="questionName"
								class="form-control" type="text" />
						</div>
						<div class="col-md-2">
							<p>Answer 1</p>
						</div>
						<div class="col-md-8">
							<input name="answer1" class="form-control" type="text" />
						</div>
						<div class="col-md-2">
							<button class="btn btn-sm btn-outline-success" id="correct1">Correct Answer</button>
						</div>
						<div class="col-md-2">
							<p>Answer 2</p>
						</div>
						<div class="col-md-8">
							<input name="answer2" class="form-control" type="text" />
						</div>
						<div class="col-md-2">
							<button class="btn btn-sm btn-outline-success" id="correct2">Correct Answer</button>
						</div>
						<div class="col-md-2">
							<p>Answer 3</p>
						</div>
						<div class="col-md-8">
							<input name="answer3" class="form-control" type="text" />
						</div>
						<div class="col-md-2">
							<button class="btn btn-sm btn-outline-success" id="correct3">Correct Answer</button>
						</div>
						<div class="col-md-2">
							<p>Answer 4</p>
						</div>
						<div class="col-md-8">
							<input name="answer4" class="form-control" type="text" />
						</div>
						<div class="col-md-2">
							<button class="btn btn-sm btn-outline-success" id="correct4">Correct Answer</button>
						</div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
							<button class="btn btn-lg btn-outline-success" type="submit">Create</button>
						</div>
					</div>
				</form>
			</div>
			<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
		</body>
		<script>
		
		function parseGET() {
            var get = {}; 
            var query = document.location
                .toString()
                // get the query string
                .replace(/^.*?\?/, '') 
                // and remove any existing hash string (thanks, @vrijdenker)
                .replace(/#.*$/, '') 
                .split('&');
            for (var i = 0, l=query.length; i<l; i++) {
                var aux = decodeURIComponent(query[i]).split('=');
                get[aux[0]] = aux[1]
            }   
            return get;
        }
		
		window.onload = function () {
			var getVars = parseGET();
	        document.getElementById('addQuestionForm').action = "add-question?quiz_id=" + getVars['quiz_id'];
		};
		</script>
</html>