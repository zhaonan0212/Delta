Private Sub Workbook_Open()
    Worksheets("TemplateVersion").Activate
    
End Sub

Private Sub Workbook_BeforeSave(ByVal SaveAsUI As Boolean, Cancel As Boolean)

    Dim full_Path As String
    
    Dim valuesWorksheet As Worksheet
    Set valuesWorksheet = ThisWorkbook.Sheets("Values")
    Dim tableRelationTypeValues As Variant
    Dim cellValue As Variant
        
    tableRelationTypeValues = valuesWorksheet.Range("MappingTypes").Value
    Debug.Print VarType(tableRelationTypeValues)
    For Each cellValue In tableRelationTypeValues
        cellValueTrimmed = Trim(cellValue)
        If cellValueTrimmed = "" Then
            Debug.Print "DoNothing"
        Else
            Debug.Print cellValueTrimmed
        End If
    Next cellValue
        
    full_Path = Mid(Me.FullName, 1, InStrRev(Me.FullName, "\")) & "continue.txt"
    If Trim(Dir(full_Path)) = "" Then
        'Call Check_CDM
    End If
    
End Sub


