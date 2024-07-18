// script.js

function openCommentForm() {
    const commentForm = document.getElementById('commentForm');
    if (commentForm.style.display === 'block') {
        commentForm.style.display = 'none';
    } else {
        commentForm.style.display = 'block';
    }
}

function submitComment() {
    const commentText = document.getElementById('commentText').value;
    if (!commentText) {
        alert('댓글을 입력하세요.');
        return;
    }

    // 댓글 작성 후 폼 숨기기
    document.getElementById('commentForm').style.display = 'none';
}
