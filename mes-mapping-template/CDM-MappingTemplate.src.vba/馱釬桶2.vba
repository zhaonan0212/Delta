Dim OldValue
Private Sub Worksheet_Change(ByVal Target As Range)
    Dim m As Integer, n As Integer
    Dim xlWorksheet2 As Worksheet
    
    Debug.Print "change13"
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    m = Target.Row
    
    If OldValue = "" Then
        Exit Sub
    Else
        If Target.Value <> OldValue Then
        xlWorksheet2.Cells(m, 29).Interior.ColorIndex = 6
        End If
    End If
End Sub

Private Sub Worksheet_SelectionChange(ByVal Target As Range)
    OldValue = Target.Value
    Debug.Print "Worksheet123"
End Sub