package me.s097t0r1.core.ui_components.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.s097t0r1.core.ui_components.theme.KtCastColorPallete
import me.s097t0r1.core.ui_components.theme.KtCastTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KtCastOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(16.dp),
    colors: KtCastTextFieldColors = KtCastTextFieldColors(
        defaultTextColor = KtCastTheme.colors.textPrimaryColor,
        disabledTextColor = KtCastTheme.colors.textPlaceholderColor.copy(alpha = 0.7f),
        cursorColor = KtCastTheme.colors.textPrimaryColor,
        errorCursorColor = KtCastTheme.colors.textPrimaryColor,
        placeholderColor = KtCastTheme.colors.textPlaceholderColor,
        disabledPlaceholderColor = KtCastTheme.colors.textPlaceholderColor.copy(alpha = 0.7f),
        defaultIconColor = KtCastTheme.colors.textPlaceholderColor,
        disabledIconColor = KtCastTheme.colors.textPlaceholderColor.copy(alpha = 0.7f),
        errorIconColor = KtCastColorPallete.statusErrorColor,
        focusedIconColor = KtCastTheme.colors.primaryColor,
        focusedBackgroundColor = KtCastTheme.colors.fieldActiveBackgroundColor,
        defaultBackgroundColor = KtCastTheme.colors.fieldDefaultBackgroundColor,
        disabledBackgroundColor = KtCastTheme.colors.fieldDefaultBackgroundColor.copy(alpha = 0.7f)
    )
) {

    val isFocused by interactionSource.collectIsFocusedAsState()

    val currentTextColor = textStyle.color.takeOrElse {
        colors.textColor(enabled = enabled).value
    }
    val currentTextWeight by remember {
        mutableStateOf(if (value.isEmpty()) FontWeight.Normal else FontWeight.SemiBold)
    }

    val inputFieldTextStyle = textStyle.merge(
        TextStyle(
            color = currentTextColor,
            fontWeight = currentTextWeight
        )
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .background(colors.backgroundColor(enabled, isFocused).value, shape)
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        visualTransformation = visualTransformation,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = inputFieldTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        maxLines = maxLines,
        interactionSource = interactionSource,
    ) { innerTextField ->

        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = enabled,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            isError = isError,
            label = null,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            colors = if (value.isNotEmpty()) {
                colors.copy(defaultIconColor = KtCastTheme.colors.textPrimaryColor)
            } else {
                colors.copy(defaultIconColor = KtCastTheme.colors.textPlaceholderColor)
            },
            contentPadding = PaddingValues(
                vertical = KtCastTextFieldVerticalPadding,
                horizontal = KtCastTextFieldHorizontalPadding
            ),
            border = {
                TextFieldDefaults.BorderBox(
                    enabled = enabled,
                    isError = isError,
                    colors = colors,
                    shape = shape,
                    interactionSource = interactionSource,
                    focusedBorderThickness = KtCastFocusedBorderThickness,
                )
            }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    AnimatedVisibility(visible = isError) {
        Text(
            text = errorText,
            style = textStyle.copy(color = KtCastColorPallete.statusErrorColor)
        )
    }
}

internal val KtCastFocusedBorderThickness = 1.dp
internal val KtCastTextFieldVerticalPadding = 18.dp
internal val KtCastTextFieldHorizontalPadding = 12.dp

@OptIn(ExperimentalMaterialApi::class)
@Immutable
class KtCastTextFieldColors(
    val defaultTextColor: Color,
    val disabledTextColor: Color,
    val cursorColor: Color,
    val errorCursorColor: Color,
    val defaultIconColor: Color,
    val focusedIconColor: Color,
    val disabledIconColor: Color,
    val errorIconColor: Color,
    val placeholderColor: Color,
    val disabledPlaceholderColor: Color,
    val defaultBackgroundColor: Color,
    val focusedBackgroundColor: Color,
    val disabledBackgroundColor: Color
) : TextFieldColorsWithIcons {

    @Composable
    fun backgroundColor(enabled: Boolean, isFocused: Boolean): State<Color> {
        return rememberUpdatedState(
            when {
                !enabled -> disabledBackgroundColor
                isFocused -> focusedBackgroundColor
                else -> defaultBackgroundColor
            }
        )
    }

    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(
            when {
                !enabled -> disabledBackgroundColor
                else -> defaultBackgroundColor
            }
        )
    }

    @Composable
    override fun cursorColor(isError: Boolean): State<Color> {
        return rememberUpdatedState(
            when {
                isError -> errorCursorColor
                else -> cursorColor
            }
        )
    }

    @Composable
    override fun indicatorColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val isFocused by interactionSource.collectIsFocusedAsState()
        val target = when {
            isFocused -> focusedIconColor
            else -> Color.Transparent
        }
        return animateColorAsState(target)
    }

    @Composable
    override fun labelColor(
        enabled: Boolean,
        error: Boolean,
        interactionSource: InteractionSource
    ): State<Color> = rememberUpdatedState(Color.Transparent)

    @Composable
    override fun leadingIconColor(enabled: Boolean, isError: Boolean): State<Color> {
        if (!enabled) return rememberUpdatedState(this.disabledIconColor)
        return animateColorAsState(
            when {
                isError -> this.errorIconColor
                else -> this.defaultIconColor
            }
        )
    }

    @Composable
    override fun placeholderColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) placeholderColor else disabledPlaceholderColor)

    @Composable
    override fun textColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) defaultTextColor else disabledTextColor)

    @Composable
    override fun trailingIconColor(enabled: Boolean, isError: Boolean): State<Color> {
        if (!enabled) return rememberUpdatedState(this.disabledIconColor)
        return animateColorAsState(
            when {
                isError -> this.errorIconColor
                else -> this.defaultIconColor
            }
        )
    }

    @Composable
    override fun leadingIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        if (!enabled) return rememberUpdatedState(this.disabledIconColor)
        val isFocused by interactionSource.collectIsFocusedAsState()
        return animateColorAsState(
            when {
                isFocused -> this.focusedIconColor
                isError -> this.errorIconColor
                else -> this.defaultIconColor
            }
        )
    }

    @Composable
    override fun trailingIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        if (!enabled) return rememberUpdatedState(this.disabledIconColor)
        val isFocused by interactionSource.collectIsFocusedAsState()
        return animateColorAsState(
            when {
                isFocused -> this.focusedIconColor
                isError -> this.errorIconColor
                else -> this.defaultIconColor
            }
        )
    }

    fun copy(
        defaultTextColor: Color = this.defaultTextColor,
        disabledTextColor: Color = this.disabledTextColor,
        cursorColor: Color = this.cursorColor,
        errorCursorColor: Color = this.errorCursorColor,
        defaultIconColor: Color = this.defaultIconColor,
        focusedIconColor: Color = this.focusedIconColor,
        disabledIconColor: Color = this.disabledIconColor,
        errorIconColor: Color = this.errorIconColor,
        placeholderColor: Color = this.placeholderColor,
        disabledPlaceholderColor: Color = this.disabledPlaceholderColor,
        defaultBackgroundColor: Color = this.defaultBackgroundColor,
        focusedBackgroundColor: Color = this.focusedBackgroundColor,
        disabledBackgroundColor: Color = this.disabledBackgroundColor,
    ) = KtCastTextFieldColors(
        defaultTextColor,
        disabledTextColor,
        cursorColor,
        errorCursorColor,
        defaultIconColor,
        focusedIconColor,
        disabledIconColor,
        errorIconColor,
        placeholderColor,
        disabledPlaceholderColor,
        defaultBackgroundColor,
        focusedBackgroundColor,
        disabledBackgroundColor
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as KtCastTextFieldColors

        if (this.defaultTextColor != other.defaultTextColor) return false
        if (this.disabledTextColor != other.disabledTextColor) return false
        if (this.cursorColor != other.cursorColor) return false
        if (this.errorCursorColor != other.errorCursorColor) return false
        if (this.defaultIconColor != other.defaultIconColor) return false
        if (this.focusedIconColor != other.focusedIconColor) return false
        if (this.disabledIconColor != other.disabledIconColor) return false
        if (this.errorIconColor != other.errorIconColor) return false
        if (this.placeholderColor != other.placeholderColor) return false
        if (this.disabledPlaceholderColor != other.disabledPlaceholderColor) return false
        if (this.defaultBackgroundColor != other.defaultBackgroundColor) return false
        if (this.focusedBackgroundColor != other.focusedBackgroundColor) return false
        if (this.disabledBackgroundColor != other.disabledBackgroundColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = defaultTextColor.hashCode()
        result = 31 * result + disabledTextColor.hashCode()
        result = 31 * result + cursorColor.hashCode()
        result = 31 * result + errorCursorColor.hashCode()
        result = 31 * result + defaultIconColor.hashCode()
        result = 31 * result + focusedIconColor.hashCode()
        result = 31 * result + disabledIconColor.hashCode()
        result = 31 * result + errorIconColor.hashCode()
        result = 31 * result + placeholderColor.hashCode()
        result = 31 * result + disabledPlaceholderColor.hashCode()
        result = 31 * result + defaultBackgroundColor.hashCode()
        result = 31 * result + focusedBackgroundColor.hashCode()
        result = 31 * result + disabledBackgroundColor.hashCode()

        return result
    }
}

@Preview
@Composable
fun OutlineTextFieldPreview() {
    KtCastTheme {

        val (value, onValueChanged) = remember { mutableStateOf("") }

        Column {
            KtCastOutlinedTextField(
                value = value,
                isError = true,
                errorText = "Lorem ipsum",
                leadingIcon = {
                    // TODO
                },
                onValueChange = onValueChanged,
                placeholder = { Text("Placeholder") }
            )
        }
    }
}